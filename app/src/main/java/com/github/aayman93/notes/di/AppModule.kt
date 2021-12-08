package com.github.aayman93.notes.di

import android.content.Context
import androidx.room.Room
import com.github.aayman93.notes.data.database.NotesDao
import com.github.aayman93.notes.data.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val NOTES_DATABASE_NAME = "notes_db"

    @Singleton
    @Provides
    fun provideNotesDatabase(
        @ApplicationContext appContext: Context
    ): NotesDatabase = Room.databaseBuilder(
        appContext,
        NotesDatabase::class.java,
        NOTES_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNotesDao(
        database: NotesDatabase
    ): NotesDao = database.getNotesDao()
}