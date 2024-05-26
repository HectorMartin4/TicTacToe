package com.rubilax.a3enraya.feature.domain

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import javax.inject.Inject

class GetBoardSquaresUseCase @Inject constructor(private val repository: GameRepository) {

    operator fun invoke() : Either<ErrorApp, List<BoardSquare>> = repository.getBoard()
}