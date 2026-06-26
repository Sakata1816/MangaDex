package com.example.dota2.data.repository.di

import com.example.dota2.data.repository.auth.AuthRepositoryImpl
import com.example.dota2.data.repository.profile.FavoriteRepositoryImpl
import com.example.dota2.data.repository.profile.ProfileRepositoryImpl
import com.example.dota2.data.repository.server.MangaRepositoryImpl
import com.example.dota2.domain.repository.auth.AuthRepository
import com.example.dota2.domain.repository.profile.FavoriteRepository
import com.example.dota2.domain.repository.profile.ProfileRepository
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

     @Binds
     @Singleton
     abstract fun bindsProfileRepo(
         impl: ProfileRepositoryImpl
     ): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindsFavoriteRepo(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepo(
        impl: AuthRepositoryImpl
    ): AuthRepository


}
