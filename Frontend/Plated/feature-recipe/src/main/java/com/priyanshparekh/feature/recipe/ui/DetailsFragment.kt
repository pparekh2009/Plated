package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.feature.recipe.R
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.databinding.FragmentDetailsBinding
import kotlin.getValue

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val recipeViewModel: RecipeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameInput.setText("${recipeViewModel.recipe.value?.recipeName}")
        binding.descriptionInput.setText("${recipeViewModel.recipe.value?.description}")
        binding.servingsInput.setText("${recipeViewModel.recipe.value?.servingSize}")

        val time = recipeViewModel.recipe.value?.cookingTime ?: 0L

        if (time > 60) {
            binding.timeInput.setText("${ time / 60 }")
            binding.timeUnitInput.setText("hrs")
        } else {
            binding.timeInput.setText("$time")
            binding.timeUnitInput.setText("mins")
        }

        binding.cuisineInput.setText("${recipeViewModel.recipe.value?.cuisine}")
        binding.categoryInput.setText("${recipeViewModel.recipe.value?.category}")

        val timeUnits = resources.getStringArray(R.array.time_units)
        binding.timeUnitInput.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, timeUnits))
        binding.timeUnitInput.setText("mins", false)

        val cuisines = resources.getStringArray(R.array.cuisines)
        binding.cuisineInput.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cuisines))

        val categories = resources.getStringArray(R.array.categories)
        binding.categoryInput.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories))


        binding.btnNext.setOnClickListener {
            recipeViewModel.updateName(binding.nameInput.text.toString())
            recipeViewModel.updateDescription(binding.descriptionInput.text.toString())
            recipeViewModel.updateServings(if (binding.servingsInput.text.toString().isEmpty()) 0 else binding.servingsInput.text.toString().toInt())
            var timeInput = if (binding.timeInput.text.toString().isEmpty()) {
                0L
            } else {
                val time = binding.timeInput.text.toString().toLong()
                if (binding.timeUnitInput.text.toString() == "hrs") {
                    time * 60L
                } else {
                    time
                }
            }

            recipeViewModel.updateCookingTime(timeInput)
            recipeViewModel.updateCuisine(binding.cuisineInput.text.toString())
            recipeViewModel.updateCategory(binding.categoryInput.text.toString())

            NavigatorProvider.recipeNavigator.openIngredientFragment(findNavController())
        }

        binding.btnBack.setOnClickListener {
            BackConfirmationDialog {
                recipeViewModel.resetRecipe()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }.show(parentFragmentManager, "ShowConfirmationDialog")
        }
    }
}