package com.example.dota2.data.local.di

import android.app.Application
import androidx.room.Room
import com.example.dota2.data.local.DAO.FavoriteMangaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration() // 👈 ДОБАВЬ СЮДА
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): FavoriteMangaDao {
        return db.favoriteMangaDao()
    }


}