package com.example.dota2.data.repository.di

import com.example.dota2.data.repository.server.MangaRepositoryImpl
import com.example.dota2.domain.repository.server.MangaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MangaRepositoryDi {

    @Binds
    @Singleton
     abstract  fun bindsMangaRepo(
        impl: MangaRepositoryImpl
    ): MangaRepository
}
