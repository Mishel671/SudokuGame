package ru.dzyubamichael.sudokugameapp.data.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_items")
data class GameDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Bitmap,
    val gameData: Array<IntArray>
)