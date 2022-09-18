package com.aaron.spique.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class PhraseEntity(
    @PrimaryKey val phrase_id: Int,
    val text: String,
    val color: String
)