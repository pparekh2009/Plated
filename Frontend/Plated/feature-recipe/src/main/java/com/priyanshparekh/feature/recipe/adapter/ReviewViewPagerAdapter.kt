package com.priyanshparekh.feature.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.utils.MarginItemDecoration
import com.priyanshparekh.feature.recipe.R

class ReviewViewPagerAdapter(val context: Context, ingredients: ArrayList<RecipeIngredientDto>, instructions: ArrayList<StepDto>): RecyclerView.Adapter<ReviewViewPagerAdapter.ViewHolder>() {

    private val ingredientsAdapter = IngredientsAdapter(ingredients)
    private val instructionsAdapter = InstructionAdapter(instructions)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.recyclerView.adapter = ingredientsAdapter
            }
            1 -> {
                holder.recyclerView.adapter = instructionsAdapter
            }
        }

        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.addItemDecoration(MarginItemDecoration(context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))
    }

    override fun getItemCount(): Int = 2

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)!!
    }

    fun refreshData() {
        ingredientsAdapter.notifyDataSetChanged()
        instructionsAdapter.notifyDataSetChanged()
    }
}