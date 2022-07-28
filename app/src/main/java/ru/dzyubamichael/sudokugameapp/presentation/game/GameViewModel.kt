package ru.dzyubamichael.sudokugameapp.presentation.game

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.dzyubamichael.sudokugameapp.domain.AddGameItemUseCase
import ru.dzyubamichael.sudokugameapp.domain.GameItem
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val addGameItemUseCase: AddGameItemUseCase
): ViewModel() {

    fun addGame(gameData: Array<IntArray>, logo:Bitmap){
        viewModelScope.launch(Dispatchers.IO) {

            val gameItem = GameItem(
                image = logo,
                gameData = gameData
            )
            addGameItemUseCase(gameItem)
        }
    }
    fun updateGame(gameData: Array<IntArray>, logo:Bitmap, id:Int){
        viewModelScope.launch(Dispatchers.IO) {

            val gameItem = GameItem(
                id = id,
                image = logo,
                gameData = gameData
            )
            addGameItemUseCase(gameItem)
        }
    }
}