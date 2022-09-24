package com.aaron.spique.ui.phrasegroups

import com.aaron.spique.ui.shared.recyclerview.baserecycleradapter.BaseRecyclerAdapter
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseItemUiState
import java.util.*

data class PhraseGroupItemUiState(
    override val id: String = UUID.randomUUID().toString(),
    val header: String,
    val phraseItemList: List<PhraseItemUiState>
): BaseRecyclerAdapter.Item() {

    // Override this so that comparing item list is handled by the child adapter
    override fun areContentsTheSame(otherItem: BaseRecyclerAdapter.Item): Boolean {
        return id == otherItem.id && header == (otherItem as PhraseGroupItemUiState).header
    }
}