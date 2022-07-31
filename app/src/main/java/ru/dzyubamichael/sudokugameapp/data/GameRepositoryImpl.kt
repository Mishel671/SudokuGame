package ru.dzyubamichael.sudokugameapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.dzyubamichael.sudokugameapp.data.database.GamesListDao
import ru.dzyubamichael.sudokugameapp.domain.GameItem
import ru.dzyubamichael.sudokugameapp.domain.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gamesListDao: GamesListDao
) : GameRepository {

    override fun getGamesList(): LiveData<List<GameItem>> {
        return Transformations.map(
            gamesListDao.getGamesList()
        ) { list ->
            list.map { dbModel ->
                dbModel.mapToItem()
            }
        }
    }

    override suspend fun addGameItem(gameItem: GameItem) {
        gamesListDao.addGameItem(gameItem.mapToDbModel())
    }

    override suspend fun deleteGameItem(id: Int) {
        gamesListDao.deleteGameItem(id)
    }
}