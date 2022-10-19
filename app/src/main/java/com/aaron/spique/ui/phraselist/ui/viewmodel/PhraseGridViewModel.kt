package com.aaron.spique.ui.phraselist.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.aaron.spique.core.extensions.swap
import com.aaron.spique.data.repository.PhraseRepository
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseItemUiState
import com.aaron.spique.ui.shared.SpeakingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhraseGridViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val colorMapper: ColorMapper
) : SpeakingViewModel() {

    private val _uiState = MutableStateFlow(PhraseGridUiState())
    val uiState: StateFlow<PhraseGridUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { state ->
                val newPhraseItems =
                    phraseRepository.getPhrasesForTags(listOf()).map { phraseModel ->
                        val color = colorMapper.getColorFromString(phraseModel.color)
                        val phrase = phraseModel.phrase
                        PhraseItemUiState(
                            phrase = phrase,
                            isQueued = false,
                            isBeingSpoken = false,
                            isFilteredOut = false,
                            color = color
                        )
                    }
                state.copy(phrases = newPhraseItems)
            }
            utterances.collectLatest { utteranceList ->
                val mutablePhrases = uiState.value.phrases.toMutableList()
                uiState.value.phrases.forEach { itemUiState ->
                    utteranceList.firstOrNull { it.phraseViewItemId == itemUiState.id }?.apply {
                        Collections.replaceAll(
                            mutablePhrases, itemUiState, itemUiState.copy(
                                isBeingSpoken = this.isBeingSpoken,
                                isQueued = true
                            )
                        )
                    } ?: run {
                        Collections.replaceAll(
                            mutablePhrases, itemUiState, itemUiState.copy(
                                isBeingSpoken = false,
                                isQueued = false
                            )
                        )
                    }
                }
                _uiState.update { it.copy(phrases = mutablePhrases) }
            }
        }
    }

    fun itemsMoved(from: Int, to: Int) {
        _uiState.update {
            it.copy(phrases = it.phrases.toMutableList().apply { swap(from, to) })
        }
    }

    fun filterItems(filterText: CharSequence?) {
        filterText?.let { filter ->
            _uiState.update { state ->
                state.copy(phrases = state.phrases.map { phraseItem ->
                    phraseItem.copy(isFilteredOut = !phraseItem.phrase.contains(filter))
                })
            }
        }
    }

    data class PhraseGridUiState(
        val phrases: List<PhraseItemUiState> = listOf()
    )
}