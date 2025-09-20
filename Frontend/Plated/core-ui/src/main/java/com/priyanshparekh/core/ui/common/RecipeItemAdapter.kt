package com.priyanshparekh.core.ui.common;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.RecipeItem
import com.priyanshparekh.core.ui.R
import com.priyanshparekh.core.utils.OnRvItemClickListener
import kotlin.math.roundToInt
import kotlin.text.format

class RecipeItemAdapter(val recipeItems: List<RecipeItem>, val onRvItemClickListener: OnRvItemClickListener, val isProfileFeed: Boolean) : RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recipe_item, parent, false)
        return ViewHolder(view, onRvItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeItems[position])
    }

    override fun getItemCount(): Int = recipeItems.size

    inner class ViewHolder(val view: View, val onRvItemClickListener: OnRvItemClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val recipeNameTv: TextView = view.findViewById<TextView>(R.id.recipe_name)
        val userNameTv: TextView = view.findViewById<TextView>(R.id.user_name)
        val cookingTimeTv: TextView = view.findViewById<TextView>(R.id.recipe_time)

        fun bind(recipeItem: RecipeItem) {

            recipeNameTv.text = recipeItem.recipeName
            var cookingTime = recipeItem.cookingTime
            if (cookingTime > 60) {
                cookingTime = cookingTime / 60
                cookingTimeTv.text = "%.0f hrs".format(recipeItem.cookingTime)
            } else {
                cookingTimeTv.text = "%.0f mins".format(recipeItem.cookingTime)
            }

            if (isProfileFeed) {
                userNameTv.visibility = View.GONE
            } else {
                userNameTv.text = recipeItem.displayName
            }
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onRvItemClickListener.onClick(adapterPosition)
        }
    }
}