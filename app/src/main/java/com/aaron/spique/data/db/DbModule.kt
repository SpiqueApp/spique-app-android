package com.aaron.spique.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): SpiqueDb {
        return Room.databaseBuilder(context, SpiqueDb::class.java, "db")
            .createFromAsset("database/phrases.db")
            .build()
    }
}