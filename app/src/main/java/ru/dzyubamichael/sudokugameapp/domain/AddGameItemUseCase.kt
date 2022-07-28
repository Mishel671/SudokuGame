package ru.dzyubamichael.sudokugameapp.domain

import javax.inject.Inject

class AddGameItemUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(gameItem: GameItem) = repository.addGameItem(gameItem)
}