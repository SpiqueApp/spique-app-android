package com.aaron.spique.ui.shared.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VariableColumnGridLayoutManager(val context: Context, private val itemWidthDp: Int) : GridLayoutManager(context, 2) {



    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        updateSpanCount()
        super.onLayoutChildren(recycler, state)
    }

    private fun updateSpanCount() {
        val smallerSide = context.resources.displayMetrics.let { if (it.widthPixels < it.heightPixels) it.widthPixels else it.heightPixels}
        var calculatedSpanCount = width / ((smallerSide / DP_PORTRAIT_WIDTH) * itemWidthDp)
        if (calculatedSpanCount < MIN_NUM_COLUMNS) {
            calculatedSpanCount = MIN_NUM_COLUMNS
        }
        this.spanCount = calculatedSpanCount
    }

    companion object {
        private const val DP_PORTRAIT_WIDTH = 160
        private const val MIN_NUM_COLUMNS = 2
    }
}