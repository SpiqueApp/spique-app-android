package com.aaron.spique.ui.phrasegroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.spique.data.repository.PhraseRepository
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseItemUiState
import com.aaron.spique.ui.phraselist.ui.viewmodel.ColorMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhraseGroupViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val colorMapper: ColorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhraseGroupsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val tags = phraseRepository.getTags()
            _uiState.update {
                it.copy(groupList = tags.map { tag ->
                    PhraseGroupItemUiState(
                        tag,
                        phraseRepository.getPhrasesForTags(
                            listOf(tag)
                        ).map { phraseModel ->
                            PhraseItemUiState(
                                phraseModel.phrase,
                                colorMapper.getColorFromString(phraseModel.color)
                            )
                        })
                })
            }
        }
    }

    data class PhraseGroupsUiState(
        val groupList: List<PhraseGroupItemUiState> = listOf()
    )
}