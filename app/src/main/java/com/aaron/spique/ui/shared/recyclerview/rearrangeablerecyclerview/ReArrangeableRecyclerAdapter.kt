package com.aaron.spique.ui.shared.recyclerview.rearrangeablerecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.core.extensions.swap
import com.aaron.spique.ui.shared.recyclerview.baserecycleradapter.BaseRecyclerAdapter

abstract class ReArrangeableRecyclerAdapter<T : BaseRecyclerAdapter.Item, U : RecyclerView.ViewHolder> : BaseRecyclerAdapter<U>() {

    fun swap(from: Int, to: Int) {
        val mutableList = currentList.toMutableList()
        mutableList.swap(from, to)
        submitList(mutableList)
    }
}