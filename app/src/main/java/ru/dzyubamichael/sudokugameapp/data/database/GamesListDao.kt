package ru.dzyubamichael.sudokugameapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GamesListDao {

    @Query("SELECT * FROM game_items")
    fun getGamesList(): LiveData<List<GameDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGameItem(gameDbModel: GameDbModel)

    @Query("DELETE FROM game_items WHERE id=:gameId")
    suspend fun deleteGameItem(gameId: Int)
}