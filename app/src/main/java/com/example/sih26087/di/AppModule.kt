package com.example.sih26087.di

import android.content.Context
import androidx.room.Room
import com.example.sih26087.data.local.AuthDataStore
import com.example.sih26087.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sih26087_db"
        ).fallbackToDestructiveMigration().build()
    }
}
