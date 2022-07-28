package ru.dzyubamichael.sudokugameapp.domain

import javax.inject.Inject

class GetGamesListUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke() = repository.getGamesList()
}