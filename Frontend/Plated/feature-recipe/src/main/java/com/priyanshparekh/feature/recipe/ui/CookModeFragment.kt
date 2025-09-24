package com.priyanshparekh.feature.recipe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialSplitButton
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.feature.recipe.R
import com.priyanshparekh.feature.recipe.adapter.IngredientsAdapter
import com.priyanshparekh.feature.recipe.databinding.FragmentCookModeBinding

class CookModeFragment : Fragment() {

    private lateinit var binding: FragmentCookModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCookModeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val ingredients = bundle?.get(Constants.Args.KEY_INGREDIENTS) as ArrayList<RecipeIngredientDto>
        val steps = bundle.get (Constants.Args.KEY_STEPS) as List<StepDto>

        var currentStep = 0

        val adapter = IngredientsAdapter(ingredients)

        binding.ingredientList.adapter = adapter
        binding.ingredientList.setHasFixedSize(true)
        binding.ingredientList.layoutManager = LinearLayoutManager(requireContext())
        binding.ingredientList.addItemDecoration(MarginItemDecoration(requireContext().resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.dimOverlay.visibility = View.VISIBLE
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED ||
                    newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.dimOverlay.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Animate dim based on drag
                binding.dimOverlay.alpha = slideOffset
                binding.dimOverlay.visibility = if (slideOffset > 0) View.VISIBLE else View.GONE
            }
        })

        // Allow dismiss by tapping outside
        binding.dimOverlay.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btnPrev.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                binding.tvStepNo.text = "Step ${steps[currentStep].stepNo}"
                binding.tvStep.text = steps[currentStep].step
            }
        }

        binding.btnNext.setOnClickListener {
            if (currentStep < steps.size - 1) {
                currentStep++
                binding.tvStepNo.text = "Step ${steps[currentStep].stepNo}"
                binding.tvStep.text = steps[currentStep].step
            }

            binding.btnNavigateSteps
        }
    }
}