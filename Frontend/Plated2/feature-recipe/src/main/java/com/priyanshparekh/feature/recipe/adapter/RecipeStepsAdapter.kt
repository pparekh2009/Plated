package com.priyanshparekh.feature.recipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.core.utils.OnRvItemClickListener
import com.priyanshparekh.feature.recipe.R
import java.util.ArrayList

class RecipeStepsAdapter(private val stepsList: ArrayList<StepDto>, private val onRvItemClickListener: OnRvItemClickListener): RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_step_item, parent, false)
        return ViewHolder(view, onRvItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stepIndex.text = "${holder.adapterPosition + 1}."
        holder.stepContent.text = stepsList[position].step
    }

    override fun getItemCount(): Int = stepsList.size

    class ViewHolder(view: View, private val onRvItemClickListener: OnRvItemClickListener): RecyclerView.ViewHolder(view), OnClickListener {
        val stepIndex: TextView = view.findViewById(R.id.step_index)
        val stepContent: TextView = view.findViewById(R.id.step_content)
        private val menu: ImageView = view.findViewById(R.id.menu)

        init {
            menu.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRvItemClickListener.onClick(adapterPosition, v!!)
        }


    }
}