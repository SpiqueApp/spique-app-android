package com.aaron.spique.data.repository

import com.aaron.spique.core.module.IoDispatcher
import com.aaron.spique.data.db.SpiqueDb
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PhraseRepository {
    suspend fun getPhrasesForTags(tags: List<String>): List<PhraseModel>
    suspend fun getTags(): List<String>
}

class PhraseRepositoryFromDb @Inject constructor(
    private val db: SpiqueDb,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : PhraseRepository {

    override suspend fun getPhrasesForTags(tags: List<String>): List<PhraseModel> = withContext(ioDispatcher) {
        return@withContext db.phraseDao().getAll().map { PhraseModel(it.text, it.color) }
    }

    override suspend fun getTags(): List<String> {
        return listOf("All", "A", "B")
    }
}