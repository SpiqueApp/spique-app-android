package com.aaron.spique.ui.shared.models

import java.util.*

data class Utterance(
    val id: String = UUID.randomUUID().toString(),
    val isBeingSpoken: Boolean,
    val phraseViewItemId: String,
    val phrase: String
)