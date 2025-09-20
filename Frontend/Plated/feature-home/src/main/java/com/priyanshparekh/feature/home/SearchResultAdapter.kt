package com.priyanshparekh.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priyanshparekh.core.model.home.ItemType
import com.priyanshparekh.core.model.home.SearchItem
import com.priyanshparekh.core.utils.OnRvItemClickListener

class SearchResultAdapter(private val searchResults: List<SearchItem>, private val onRvItemClickListener: OnRvItemClickListener) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_USER = 1
        const val TYPE_RECIPE = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = if (viewType == TYPE_HEADER) {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_search_result_header, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.layout_search_result_item, parent, false)
        }
        return ViewHolder(view, onRvItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_HEADER -> {
                holder.bindHeader(searchResults[position].header!!)
            }
            TYPE_USER -> {
                holder.bindItem(searchResults[position].user!!.name)
            }
            TYPE_RECIPE -> {
                holder.bindItem(searchResults[position].recipe!!.name)
            }
        }
    }

    override fun getItemCount(): Int = searchResults.size

    override fun getItemViewType(position: Int): Int {
        return when (searchResults[position].type) {
            ItemType.Header -> {
                TYPE_HEADER
            }
            ItemType.User -> {
                TYPE_USER
            }
            ItemType.Recipe -> {
                TYPE_RECIPE
            }
        }
    }

    inner class ViewHolder(val view: View, private val onRvItemClickListener: OnRvItemClickListener): RecyclerView.ViewHolder(view), OnClickListener {
        fun bindHeader(header: String) {
            val headerTv = view.findViewById<TextView>(R.id.header)
            headerTv.text = header
        }

        fun bindItem(item: String) {
            val itemTv = view.findViewById<TextView>(R.id.item)
            itemTv.text = item
        }

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onRvItemClickListener.onClick(adapterPosition)
        }
    }
}