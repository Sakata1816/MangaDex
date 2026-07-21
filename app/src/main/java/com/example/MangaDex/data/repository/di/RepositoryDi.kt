package com.example.MangaDex.data.repository.di

import com.example.MangaDex.data.repository.auth.AuthRepositoryImpl
import com.example.MangaDex.data.repository.profile.FavoriteRepositoryImpl
import com.example.MangaDex.data.repository.profile.ProfileRepositoryImpl
import com.example.MangaDex.data.repository.server.MangaRepositoryImpl
import com.example.MangaDex.data.repository.theme.ThemeRepositoryImpl
import com.example.MangaDex.domain.repository.auth.AuthRepository
import com.example.MangaDex.domain.repository.profile.FavoriteRepository
import com.example.MangaDex.domain.repository.profile.ProfileRepository
import com.example.MangaDex.domain.repository.server.MangaRepository
import com.example.MangaDex.domain.repository.theme.ThemeRepository
import dagger.Binds
import dagger.Module
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

    @Binds
    @Singleton
    abstract fun bindThemeRepo(
        impl: ThemeRepositoryImpl
    ): ThemeRepository

}
