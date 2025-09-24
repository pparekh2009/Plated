package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.RecipeItem
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.ui.common.RecipeItemAdapter
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.core.utils.OnRvItemClickListener
import com.priyanshparekh.core.utils.SharedPrefManager
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.databinding.FragmentSavedRecipesBinding

class SavedRecipesFragment : Fragment() {

    private lateinit var binding: FragmentSavedRecipesBinding
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSavedRecipesBinding.inflate(layoutInflater)

        val userId = SharedPrefManager.getUserId()
        recipeViewModel.getSavedRecipes(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedRecipes = ArrayList<RecipeItem>()
        val savedRecipesAdapter = RecipeItemAdapter(savedRecipes, object : OnRvItemClickListener {
            override fun onClick(position: Int) {
                super.onClick(position)

                NavigatorProvider.commonNavigator.openRecipeDetailFragment(findNavController(), savedRecipes[position].recipeId)
            }
        }, isProfileFeed = false)

        binding.rvSavedRecipes.adapter = savedRecipesAdapter
        binding.rvSavedRecipes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvSavedRecipes.setHasFixedSize(true)
        binding.rvSavedRecipes.addItemDecoration(MarginItemDecoration(requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))

        recipeViewModel.savedRecipesStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: savedRecipesStatus: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    savedRecipes.clear()
                    savedRecipes.addAll(status.data)

                    savedRecipesAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}