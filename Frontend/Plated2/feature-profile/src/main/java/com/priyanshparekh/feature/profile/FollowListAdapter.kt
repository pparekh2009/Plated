package com.priyanshparekh.feature.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.profile.FollowRelation
import com.priyanshparekh.feature.profile.FollowListAdapter.ViewHolder

class FollowListAdapter(val context: Context, val followList: List<FollowRelation>): RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_follow_list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.d("TAG", "followListAdapter: onBindViewHolder: name: ${followList[position].name}")
            holder.nameTv.text = followList[position].name
        }

        override fun getItemCount(): Int = followList.size

        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            val nameTv = view.findViewById<TextView>(R.id.tv_follow_name)!!
        }
}