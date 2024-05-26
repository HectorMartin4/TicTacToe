package com.rubilax.a3enraya.feature.di

import com.rubilax.a3enraya.feature.data.GameDataRepository
import com.rubilax.a3enraya.feature.domain.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GameModule {

    @Binds
    abstract fun bindEventDataRepository(gameDataRepository: GameDataRepository): GameRepository
}