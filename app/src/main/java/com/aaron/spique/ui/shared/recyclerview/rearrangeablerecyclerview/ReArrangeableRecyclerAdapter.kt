package com.aaron.spique.ui.shared.recyclerview.rearrangeablerecyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.core.extensions.swap

abstract class ReArrangeableRecyclerAdapter<T, U : RecyclerView.ViewHolder> : RecyclerView.Adapter<U>() {
    protected abstract val items: MutableList<T>

    @SuppressLint("NotifyDataSetChanged")
    fun update(newItems: List<T>) {
        if (newItems != items) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    fun swap(from: Int, to: Int) {
        items.swap(from, to)
        notifyItemMoved(from, to)
    }
}