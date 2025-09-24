package com.priyanshparekh.feature.home.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView
import com.priyanshparekh.core.model.home.ItemType
import com.priyanshparekh.core.model.home.SearchItem
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.core.utils.OnRvItemClickListener
import com.priyanshparekh.feature.home.HomeViewModel
import com.priyanshparekh.feature.home.SearchResultAdapter
import com.priyanshparekh.feature.home.databinding.FragmentSearchBinding
import kotlin.getValue

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.show()

        binding.searchView.addTransitionListener { searchView, previousState, newState ->
            if (newState == SearchView.TransitionState.HIDING) {
                Log.d("TAG", "onViewCreated: homeFragment: new state: $newState")
                binding.searchView.editText.clearFocus()
                binding.searchView.editText.setText("")
            } else if (newState == SearchView.TransitionState.HIDDEN) {
                Log.d("TAG", "onViewCreated: homeFragment: new state: $newState")
                findNavController().navigateUp()
            }
        }

        var searchItems = ArrayList<SearchItem>()

        Log.d("TAG", "onViewCreated: homeFragment: searchItems size: ${searchItems.size}")

        val adapter = SearchResultAdapter(searchItems, object : OnRvItemClickListener {
            override fun onClick(position: Int) {
                super.onClick(position)

                val item = searchItems[position]

                if (item.type == ItemType.User) {
                    Log.d("TAG", "adapter onClick: homeFragment: user")
                    NavigatorProvider.commonNavigator.openProfileFragment(findNavController(), item.user!!.id, false)

                } else if (item.type == ItemType.Recipe) {
                    Log.d("TAG", "adapter onClick: homeFragment: recipe")
                    NavigatorProvider.commonNavigator.openRecipeDetailFragment(findNavController(), item.recipe!!.id)
                }
            }
        })


        // -------------------------
        // 1. Setup RecyclerView + Adapter
        // -------------------------
        binding.searchResultsRv.adapter = adapter
        binding.searchResultsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsRv.setHasFixedSize(true)
        binding.searchResultsRv.addItemDecoration(MarginItemDecoration(requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))


        // -------------------------
        // 2. Setup SearchView Text listeners
        // -------------------------
        binding.searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()

                Log.d("TAG", "onTextChanged: homeFragment: query: $query")

                when(s?.length) {
                    in 0 until 3 -> {
                        Log.d("TAG", "onTextChanged: homeFragment: < 3")
                        searchItems.clear()
                        adapter.notifyDataSetChanged()
                    }
                    3 -> {
                        Log.d("TAG", "onTextChanged: homeFragment: query: $query")
                        Log.d("TAG", "onTextChanged: homeFragment: ==")
                        homeViewModel.getSearchResults(query)
                    }
                    else -> {
                        Log.d("TAG", "onTextChanged: homeFragment: else")
                        searchItems.retainAll { result ->
                            when (result.type) {
                                ItemType.User -> result.user!!.name.contains(query, ignoreCase = true) == true
                                ItemType.Recipe -> result.recipe!!.name.contains(query, ignoreCase = true) == true
                                ItemType.Header -> true
                            }
                        }
                        adapter.notifyDataSetChanged()
                        Log.d("TAG", "onTextChanged: homeFragment: search items size: ${searchItems.size}")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) { }

        })

        homeViewModel.searchStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "onViewCreated: homeViewModel: searchStatus: error: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    with(status.data.users) {
                        Log.d("TAG", "onViewCreated: Users")


                        if (this != null && this.isNotEmpty()) {

                            this.forEach {
                                Log.d("TAG", "onViewCreated: ${it.name}")
                            }

                            searchItems.add(SearchItem("Users", type = ItemType.Header))
                            searchItems.addAll(this.map {
                                return@map SearchItem(user = it, type = ItemType.User)
                            })
                        }
                    }

                    with(status.data.recipes) {
                        Log.d("TAG", "onViewCreated: Recipes")


                        if (this != null && this.isNotEmpty()) {

                            this.forEach {
                                Log.d("TAG", "onViewCreated: ${it.name}")
                            }

                            searchItems.add(SearchItem("Recipes", type = ItemType.Header))
                            searchItems.addAll(this.map {
                                return@map SearchItem(recipe = it, type = ItemType.Recipe)
                            })
                        }
                    }

                    adapter.notifyDataSetChanged()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }
}