package com.priyanshparekh.feature.recipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.feature.recipe.R

class IngredientsAdapter(private val ingredients: ArrayList<RecipeIngredientDto>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientName.text = ingredients[position].name
        holder.ingredientQty.text = ingredients[position].quantity.toString() + " " + ingredients[position].unit
    }

    override fun getItemCount(): Int = ingredients.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName = view.findViewById<TextView>(R.id.ingredient_name)!!
        val ingredientQty = view.findViewById<TextView>(R.id.quantity)!!
    }
}