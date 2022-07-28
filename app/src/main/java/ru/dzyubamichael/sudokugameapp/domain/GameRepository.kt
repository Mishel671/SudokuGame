package ru.dzyubamichael.sudokugameapp.domain

import androidx.lifecycle.LiveData

interface GameRepository {

    fun getGamesList():LiveData<List<GameItem>>

    suspend fun addGameItem(gameItem: GameItem)

    suspend fun deleteGameItem(id:Int)
}