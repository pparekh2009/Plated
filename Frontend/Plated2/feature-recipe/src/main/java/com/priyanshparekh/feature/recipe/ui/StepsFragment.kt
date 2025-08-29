package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.utils.OnRvItemClickListener
import com.priyanshparekh.feature.recipe.R
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.adapter.RecipeStepsAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentStepsBinding
import kotlin.getValue

class StepsFragment : Fragment(), OnRvItemClickListener {

    private lateinit var binding: FragmentStepsBinding

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    private val instructionsInRecipe = ArrayList<StepDto>()
    private lateinit var adapter: RecipeStepsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStepsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAG", "onViewCreated: stepsFragment: ingredients size: ${recipeViewModel.recipe.value.ingredientList.size}")

        recipeViewModel.recipe.value?.stepList?.let { instructionsInRecipe.addAll(it) }

        adapter = RecipeStepsAdapter(instructionsInRecipe, this)
        binding.stepsList.adapter = adapter
        binding.stepsList.layoutManager = LinearLayoutManager(requireContext())
        binding.stepsList.setHasFixedSize(false)


        binding.btnAdd.setOnClickListener {
            val instruction = binding.stepInput.text.toString()

            // TODO: - Validate input data

            instructionsInRecipe.add(StepDto(-1, instruction))
            adapter.notifyDataSetChanged()

            binding.stepInput.setText("")
        }

        binding.btnNext.setOnClickListener {
            instructionsInRecipe.forEachIndexed { index, instructionsInRecipe ->
                instructionsInRecipe.stepNo = index + 1
            }
            recipeViewModel.updateSteps(instructionsInRecipe)

            Log.d("TAG", "onViewCreated: stepsFragment: instructionsInRecipe: $instructionsInRecipe")

            NavigatorProvider.recipeNavigator.openReviewFragment(findNavController())
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onClick(position: Int, view: View) {
        Log.d("TAG", "onClick: view: ${view.javaClass.name}")
        if (view is AppCompatImageView) {
            PopupMenu(requireContext(), view).apply {
                inflate(R.menu.menu_recipe_steps)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete -> {
                            instructionsInRecipe.removeAt(position)
                            adapter.notifyDataSetChanged()
                            return@setOnMenuItemClickListener true
                        }
                        R.id.edit -> {
                            // TODO: - Add edit dialog
                            return@setOnMenuItemClickListener true
                        }
                    }
                    return@setOnMenuItemClickListener false
                }
                show()
            }
        }
    }
}