package com.priyanshparekh.feature.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshparekh.core.model.recipe.RecipeItem.ProfileRecipeItem
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.core.utils.OnRvItemClickListener
import com.priyanshparekh.core.utils.SharedPrefManager
import com.priyanshparekh.feature.profile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var isMyProfile = arguments?.getBoolean(Constants.Args.KEY_IS_MY_PROFILE)
        var userId = arguments?.getLong(Constants.Args.KEY_USER_ID) ?: -1L
        Log.d("TAG", "onViewCreated: profileFragment: isMyProfile: $isMyProfile")
        Log.d("TAG", "onViewCreated: profileFragment: userId: $userId")

        if (userId == -1L) {
            isMyProfile = true
            userId = SharedPrefManager.getUserId()
        }

        if (isMyProfile == true) {
            binding.btnFollow.visibility = View.GONE

            binding.userName.text = SharedPrefManager.getUsername()
            binding.bio.text = SharedPrefManager.getBio()
            binding.profession.text = SharedPrefManager.getProfession()

            // TODO: - Add website textview in layout file
            Log.d("TAG", "onViewCreated: profileFragment: website: ${SharedPrefManager.getWebsite()}")
        } else {
            binding.btnFollow.visibility = View.VISIBLE
        }
        profileViewModel.getUserProfile(userId)

        val recipeItemList = ArrayList<ProfileRecipeItem>()
        val recipesAdapter = RecipeItemAdapter(recipeItemList, object : OnRvItemClickListener {
            override fun onClick(position: Int) {
                super.onClick(position)

                NavigatorProvider.commonNavigator.openViewRecipeFragment(findNavController(), recipeItemList[position].recipeId)
            }
        })

        binding.recipes.adapter = recipesAdapter
        binding.recipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recipes.setHasFixedSize(true)
        binding.recipes.addItemDecoration(MarginItemDecoration(requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))

        binding.menu.setOnClickListener {
            Log.d("TAG", "onViewCreated: menu pressed")
            PopupMenu(requireContext(), it).apply {
                inflate(R.menu.menu_profile)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.logout -> {
                            SharedPrefManager.clearPrefs()
                            NavigatorProvider.commonNavigator.openLoginFragment(requireContext())
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
                show()
            }
        }

        profileViewModel.userProfileStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    Log.d("TAG", "onViewCreated: profileFragment: userProfileStatus: error: ${status.message}")
                }
                is Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                is Status.SUCCESS -> {
                    val userProfileDto = status.data

                    binding.userName.text = userProfileDto.displayName
                    binding.bio.text = userProfileDto.bio
                    binding.profession.text = userProfileDto.profession

                    // TODO: - Add website textview in layout file
                    Log.d("TAG", "onViewCreated: profileFragment: userProfileStatus: website: ${userProfileDto.website}")

                    binding.recipeCount.text = userProfileDto.recipeCount.toString()
                    binding.followersCount.text = userProfileDto.followersCount.toString()
                    binding.followingCount.text = userProfileDto.followingCount.toString()

                    recipeItemList.clear()
                    recipeItemList.addAll(userProfileDto.recipes)

                    recipesAdapter.notifyDataSetChanged()

                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


}