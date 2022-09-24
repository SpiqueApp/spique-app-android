package com.aaron.spique.ui.shared.recyclerview.rearrangeablerecyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.ui.shared.recyclerview.baserecycleradapter.BaseRecyclerAdapter

abstract class DraggableItemTouchHelperCallback<T : BaseRecyclerAdapter.Item, U : RecyclerView.ViewHolder>(
    private val adapter: ReArrangeableRecyclerAdapter<T, U>
) : ItemTouchHelper.SimpleCallback(START or END or UP or DOWN, 0) {

    abstract fun syncModelClass(from: Int, to: Int)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val from = viewHolder.adapterPosition
        val to = target.adapterPosition

        adapter.swap(from, to)
        syncModelClass(from, to)
        return true
    }

    override fun onSelectedChanged(
        viewHolder: RecyclerView.ViewHolder?,
        actionState: Int
    ) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ACTION_STATE_DRAG) viewHolder?.itemView?.alpha = 0.5f
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = 1.0f
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}