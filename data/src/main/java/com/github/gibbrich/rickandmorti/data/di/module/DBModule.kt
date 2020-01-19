package com.github.gibbrich.rickandmorti.data.di.module

import android.content.Context
import androidx.room.Room
import com.github.gibbrich.rickandmorti.data.db.AppDatabase
import com.github.gibbrich.rickandmorti.data.di.DataScope
import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    @DataScope
    fun provideDB(
        context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "rick_and_morti_db")
            .build()
    }
}