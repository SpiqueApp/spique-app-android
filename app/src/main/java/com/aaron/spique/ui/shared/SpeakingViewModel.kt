package com.aaron.spique.ui.shared

import androidx.lifecycle.ViewModel
import com.aaron.spique.ui.shared.models.Utterance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

abstract class SpeakingViewModel : ViewModel() {

    private val _utterances: MutableStateFlow<List<Utterance>> = MutableStateFlow(emptyList())
    val utterances = _utterances.asStateFlow()

    fun addUtterance(phrase: String, phraseViewItemId: String) {
        _utterances.update {
            it.toMutableList().apply { add(Utterance(phrase = phrase, isBeingSpoken = false, phraseViewItemId = phraseViewItemId)) }
        }
    }

    fun updateIsBeingSpoken(utteranceId: String?) {
        utteranceId?.let {
            _utterances.update { list ->
                val mutableList = list.toMutableList()
                val oldUtterance = list.first { it.id == utteranceId }
                Collections.replaceAll(mutableList, oldUtterance, oldUtterance.copy(isBeingSpoken = true))
                mutableList
            }
        }
    }

    fun removeUtterance(utteranceId: String?) {
        utteranceId?.let {
            _utterances.update { list ->
                list.filter { it.id != utteranceId }
            }
        }
    }
}