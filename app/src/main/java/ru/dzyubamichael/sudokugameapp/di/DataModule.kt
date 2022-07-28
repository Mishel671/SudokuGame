package ru.dzyubamichael.sudokugameapp.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.dzyubamichael.sudokugameapp.data.GameRepositoryImpl
import ru.dzyubamichael.sudokugameapp.data.database.AppDatabase
import ru.dzyubamichael.sudokugameapp.data.database.GamesListDao
import ru.dzyubamichael.sudokugameapp.domain.GameRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindGameRepository(impl: GameRepositoryImpl): GameRepository

    companion object {
        @Singleton
        @Provides
        fun provideGameListDao(
            application: Application
        ): GamesListDao {
            return AppDatabase.getInstance(application).gamesListDao()
        }
    }
}