package com.priyanshparekh.feature.recipe.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.recipe.StepDto
import com.priyanshparekh.feature.recipe.databinding.InstructionListItemBinding

class InstructionAdapter(private val instructions: ArrayList<StepDto>) : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = InstructionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: instructionNo: ${instructions[position].stepNo}")
        holder.instructionNo.text = instructions[position].stepNo.toString()
        holder.instruction.text = instructions[position].step
    }

    override fun getItemCount(): Int = instructions.size

    inner class ViewHolder(binding: InstructionListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val instructionNo = binding.instructionNo
        val instruction = binding.instruction
    }

}