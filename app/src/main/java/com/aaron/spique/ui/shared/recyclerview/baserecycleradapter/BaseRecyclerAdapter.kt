package com.aaron.spique.ui.shared.recyclerview.baserecycleradapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : RecyclerView.ViewHolder> : ListAdapter<BaseRecyclerAdapter.Item, T>(DiffCallback()){

    abstract class Item {
        abstract val id: String
        open fun areItemsTheSame(otherItem: Item): Boolean {
            return id == otherItem.id
        }
        open fun areContentsTheSame(otherItem: Item): Boolean {
            return this == otherItem
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.areItemsTheSame(newItem)
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.areContentsTheSame(newItem)
        }
    }
}