package com.aaron.spique.ui.phrasegroups

import androidx.lifecycle.viewModelScope
import com.aaron.spique.data.repository.PhraseRepository
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseItemUiState
import com.aaron.spique.ui.phraselist.ui.viewmodel.ColorMapper
import com.aaron.spique.ui.shared.SpeakingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhraseGroupViewModel @Inject constructor(
    private val phraseRepository: PhraseRepository,
    private val colorMapper: ColorMapper
) : SpeakingViewModel() {

    private val _uiState = MutableStateFlow(PhraseGroupsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val tags = phraseRepository.getTags()
            _uiState.update {
                it.copy(groupList = tags.map { tag ->
                    PhraseGroupItemUiState(
                        header = tag,
                        phraseItemList = phraseRepository.getPhrasesForTags(
                            listOf(tag)
                        ).map { phraseModel ->
                            PhraseItemUiState(
                                phrase = phraseModel.phrase,
                                isQueued = false,
                                isBeingSpoken = false,
                                isFilteredOut = false,
                                color = colorMapper.getColorFromString(phraseModel.color)
                            )
                        })
                })
            }
            utterances.collectLatest { utteranceList ->
                val mutablePhraseGroups = uiState.value.groupList.toMutableList()
                uiState.value.groupList.forEach { groupItem ->
                    val mutablePhrases = groupItem.phraseItemList.toMutableList()
                    groupItem.phraseItemList.forEach { phraseItemUiState ->
                        utteranceList.firstOrNull { it.phraseViewItemId == phraseItemUiState.id }
                            ?.apply {
                                Collections.replaceAll(
                                    mutablePhrases, phraseItemUiState, phraseItemUiState.copy(
                                        isBeingSpoken = this.isBeingSpoken,
                                        isQueued = true
                                    )
                                )
                            } ?: run {
                            Collections.replaceAll(
                                mutablePhrases, phraseItemUiState, phraseItemUiState.copy(
                                    isBeingSpoken = false,
                                    isQueued = false
                                )
                            )
                        }
                    }
                    Collections.replaceAll(
                        mutablePhraseGroups, groupItem, groupItem.copy(
                            phraseItemList = mutablePhrases
                        )
                    )
                }
                _uiState.update {
                    it.copy(groupList = mutablePhraseGroups)
                }
            }
        }
    }

    data class PhraseGroupsUiState(
        val groupList: List<PhraseGroupItemUiState> = listOf()
    )
}