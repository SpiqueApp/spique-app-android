package com.aaron.spique.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PhraseDao {
    @Query("SELECT * FROM phrases")
    fun getAll(): List<PhraseEntity>
}