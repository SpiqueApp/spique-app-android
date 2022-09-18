package com.aaron.spique.ui.phraselist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.spique.core.extensions.swap
import com.aaron.spique.data.repository.PhraseRepository
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhraseGridViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val colorMapper: ColorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhraseGridUiState())
    val uiState: StateFlow<PhraseGridUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { state ->
                val newPhraseItems = phraseRepository.getPhrasesForTags(listOf()).map { phraseModel ->
                    val color = colorMapper.getColorFromString(phraseModel.color)
                    val phrase = phraseModel.phrase
                    PhraseItemUiState(phrase, color)
                }
                state.copy(phrases = newPhraseItems)
            }
        }
    }

    fun itemsMoved(from: Int, to: Int) {
        _uiState.update {
            it.copy(phrases = it.phrases.toMutableList().apply { swap(from, to) })
        }
    }

    fun phraseSpoken(utterance: Utterance) {
        _uiState.update {
            it.copy(utterances = it.utterances.toMutableList().apply { remove(utterance) })
        }
    }

    fun phraseItemClicked(item: PhraseItemUiState) {
        _uiState.update {
            val mutableUtterances = it.utterances.toMutableList()
            mutableUtterances.add(Utterance(phrase = item.phrase))
            it.copy(utterances = mutableUtterances)
        }
    }

    data class PhraseGridUiState(
        val phrases: List<PhraseItemUiState> = listOf(),
        val utterances: List<Utterance> = listOf()
    )

    data class Utterance(
        val id: String = UUID.randomUUID().toString(),
        val phrase: String,
    )
}