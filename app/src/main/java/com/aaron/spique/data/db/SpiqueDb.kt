package com.aaron.spique.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhraseEntity::class], version = 1)
abstract class SpiqueDb : RoomDatabase() {
    abstract fun phraseDao(): PhraseDao
}