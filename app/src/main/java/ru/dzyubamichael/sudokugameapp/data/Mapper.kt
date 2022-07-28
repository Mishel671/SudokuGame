package ru.dzyubamichael.sudokugameapp.data

import ru.dzyubamichael.sudokugameapp.data.database.GameDbModel
import ru.dzyubamichael.sudokugameapp.domain.GameItem

fun GameItem.mapToDbModel(): GameDbModel = GameDbModel(
        id = this.id,
        image = this.image,
        gameData = this.gameData
)

fun GameDbModel.mapToItem(): GameItem = GameItem(
        id = this.id,
        image = this.image,
        gameData = this.gameData
)
