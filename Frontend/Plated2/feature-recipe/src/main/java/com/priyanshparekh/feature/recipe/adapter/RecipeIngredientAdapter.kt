package com.priyanshparekh.feature.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.RecipeIngredientDto
import com.priyanshparekh.feature.recipe.R

class RecipeIngredientAdapter(
    var context: Context,
    private var ingredientList: ArrayList<RecipeIngredientDto>,
    var onDelete: (View, Int) -> Unit,
    var onEdit: (View, Int) -> Unit
): RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_ingredient_item, parent, false)
        return ViewHolder(view, onDelete, onEdit)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.ingredientName.text = ingredient.name
        holder.ingredientQuantity.text = "${ingredient.quantity} ${ingredient.unit}"
    }

    override fun getItemCount(): Int = ingredientList.size


    class ViewHolder(
        view: View,
        var onDelete: (View, Int) -> Unit,
        var onEdit: (View, Int) -> Unit
    ): RecyclerView.ViewHolder(view), OnClickListener {

        var ingredientName: TextView = view.findViewById(R.id.ingredient_name)
        var ingredientQuantity: TextView = view.findViewById(R.id.ingredient_quantity)
        var btnDelete: Button = view.findViewById(R.id.btn_delete)

        init {
            btnDelete.setOnClickListener(this)
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v!!.id == R.id.btn_delete) {
                onDelete(v, adapterPosition)
            } else {
                onEdit(v, adapterPosition)
            }
        }


    }
}