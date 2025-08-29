package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.priyanshparekh.core.model.recipe.IngredientDto
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.feature.recipe.R
import com.priyanshparekh.feature.recipe.RecipeViewModel
import com.priyanshparekh.feature.recipe.adapter.RecipeIngredientAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentIngredientsBinding
import kotlin.getValue

class IngredientsFragment : Fragment() {

    private lateinit var binding: FragmentIngredientsBinding

    private val recipeViewModel: RecipeViewModel by activityViewModels()
    private val ingredientsInRecipe = ArrayList<RecipeIngredientDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentIngredientsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("TAG", "ingredientsFragment: onViewCreated: name: ${recipeViewModel.recipe.value?.recipeName}")
        Log.d("TAG", "ingredientsFragment: onViewCreated: category: ${recipeViewModel.recipe.value?.category}")

        // Set Ingredient Autocomplete drop down list
        val ingredients = mutableListOf<IngredientDto>()

        val ingredientsDropdownAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, ingredients)
        binding.ingredientInput.setAdapter(ingredientsDropdownAdapter)

        recipeViewModel.ingredients.observe(viewLifecycleOwner) { status ->
            when(status) {
                is Status.ERROR -> {
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    ingredients.addAll(status.data)
                    ingredientsDropdownAdapter.notifyDataSetChanged()
                }
            }
        }


        // Get ingredient id on clicking drop down item
        var ingredientId = -1L
        binding.ingredientInput.setOnItemClickListener { parent, view, position, id ->
            Log.d("TAG", "onViewCreated: $parent")
            if (parent != null) {
                val ingredient = parent.getItemAtPosition(position) as IngredientDto
                ingredientId = ingredient.id
                Log.d("TAG", "onViewCreated: ${ingredient.id} ${ingredient.name}")
            }
        }

        // Set unit drop down list
        val quantityUnitsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.ingredient_units))
        binding.unitSpinner.adapter = quantityUnitsAdapter

        // Set ingredient to recyclerview

        recipeViewModel.recipe.value?.ingredientList?.let { ingredientsInRecipe.addAll(it) }

        var ingredientsInRecipeAdapter: RecipeIngredientAdapter? = null
        ingredientsInRecipeAdapter = RecipeIngredientAdapter(requireContext(), ingredientsInRecipe, onDelete = { view, position ->
            ingredientsInRecipe.removeAt(position)
            ingredientsInRecipeAdapter!!.notifyItemRemoved(position)
        }, onEdit = { view, position ->
            // TODO: - Open edit dialog
            Log.d("TAG", "onClick: name: ${ingredientsInRecipe[position].name}")
        })
        binding.ingredientList.adapter = ingredientsInRecipeAdapter
        binding.ingredientList.layoutManager = LinearLayoutManager(requireContext())

        // Add ingredient to temp list
        binding.btnAdd.setOnClickListener {
            val ingredientName = binding.ingredientInput.text.toString()
            val ingredientQuantity = binding.quantityInput.text.toString().toFloat()
            val unit = binding.unitSpinner.selectedItem as String
            Log.d("TAG", "onViewCreated: unit: $unit")

            // TODO: - Validate input data

            val ingredient = RecipeIngredientDto(ingredientId, ingredientName, ingredientQuantity, unit)
            ingredientsInRecipe.add(ingredient)
            ingredientsInRecipeAdapter.notifyItemInserted(ingredientsInRecipe.size)

            binding.ingredientInput.setText("")
            binding.quantityInput.setText("")
            binding.unitSpinner.setSelection(0)
        }


        // Navigate to steps fragment
        binding.btnNext.setOnClickListener {
            recipeViewModel.updateIngredients(ingredientsInRecipe)

            NavigatorProvider.recipeNavigator.openStepsFragment(findNavController())
//            (activity as AddRecipeActivity).addFragment(StepsFragment())
        }

        binding.btnBack.setOnClickListener {

            findNavController().popBackStack()
//            (activity as AddRecipeActivity).removeFragment(this)
        }
    }
}