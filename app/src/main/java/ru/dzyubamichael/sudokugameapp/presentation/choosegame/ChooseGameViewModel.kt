package ru.dzyubamichael.sudokugameapp.presentation.choosegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.dzyubamichael.sudokugameapp.domain.DeleteGameItemUseCase
import ru.dzyubamichael.sudokugameapp.domain.GameItem
import ru.dzyubamichael.sudokugameapp.domain.GetGamesListUseCase
import javax.inject.Inject

@HiltViewModel
class ChooseGameViewModel @Inject constructor(
    private val getGamesListUseCase: GetGamesListUseCase,
    private val deleteGameItemUseCase: DeleteGameItemUseCase
): ViewModel() {

    val getGamesList = getGamesListUseCase()

    fun deleteGameItem(gameId: Int){
        viewModelScope.launch {
            deleteGameItemUseCase(gameId)
        }
    }
}