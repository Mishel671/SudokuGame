package ru.dzyubamichael.sudokugameapp.domain

import javax.inject.Inject

class DeleteGameItemUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(gameId: Int) = repository.deleteGameItem(gameId)
}