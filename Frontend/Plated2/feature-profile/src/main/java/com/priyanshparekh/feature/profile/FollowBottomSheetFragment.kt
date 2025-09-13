package com.priyanshparekh.feature.profile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.priyanshparekh.core.model.profile.FollowRelation
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.core.utils.SharedPrefManager
import com.priyanshparekh.feature.profile.databinding.LayoutFollowBottomSheetBinding

class FollowBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding: LayoutFollowBottomSheetBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFollowBottomSheetBinding.inflate(layoutInflater)

        val followType = arguments?.getString("follow_type")
        Log.d("TAG", "onCreate: followType: $followType")
        val userId = SharedPrefManager.getUserId()
        Log.d("TAG", "onCreate: userId: $userId")

        if (followType == "followers") {
            profileViewModel.getFollowers(userId)
            binding.header.text = "Followers"
        } else {
            profileViewModel.getFollowing(userId)
            binding.header.text = "Following"
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { sheet ->
            val behavior = BottomSheetBehavior.from(sheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followRelationList = ArrayList<FollowRelation>()

        val adapter = FollowListAdapter(requireContext(), followRelationList)

        binding.rvFollowRelationList.adapter = adapter
        binding.rvFollowRelationList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowRelationList.setHasFixedSize(true)
//        binding.rvFollowRelationList.addItemDecoration(MarginItemDecoration(com.intuit.sdp.R.dimen._10sdp))

        profileViewModel.followerStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: followerStatus: ${status.message}")
                    binding.progressBar.visibility = View.GONE
                }
                is Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                is Status.SUCCESS -> {

//                    followRelationList.clear()
                    followRelationList.addAll(status.data)

                    adapter.notifyDataSetChanged()

                    status.data.forEach {
                        Log.d("TAG", "onViewCreated: followerStatus: follower name: ${it.name}")
                    }

                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        profileViewModel.followingStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: followingStatus: ${status.message}")
                    binding.progressBar.visibility = View.GONE
                }
                is Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                is Status.SUCCESS -> {

//                    followRelationList.clear()
                    followRelationList.addAll(status.data)

                    adapter.notifyDataSetChanged()

                    status.data.forEach {
                        Log.d("TAG", "onViewCreated: followerStatus: following name: ${it.name}")
                    }

                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}