package com.aaron.spique.ui.shared.recyclerview.phraserecyclerview

import androidx.annotation.ColorInt
import com.aaron.spique.ui.shared.recyclerview.baserecycleradapter.BaseRecyclerAdapter
import java.util.*

data class PhraseItemUiState(
    override val id: String = UUID.randomUUID().toString(),
    val phrase: String,
    val isQueued: Boolean,
    val isBeingSpoken: Boolean,
    @ColorInt val color: Int
) : BaseRecyclerAdapter.Item()