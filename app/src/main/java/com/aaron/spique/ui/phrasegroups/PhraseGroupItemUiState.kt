package com.aaron.spique.ui.phrasegroups

import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseItemUiState

data class PhraseGroupItemUiState(
    val header: String,
    val phraseItemList: List<PhraseItemUiState>
)