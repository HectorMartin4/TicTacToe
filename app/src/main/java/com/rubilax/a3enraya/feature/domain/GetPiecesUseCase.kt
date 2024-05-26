package com.rubilax.a3enraya.feature.domain

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import javax.inject.Inject

class GetPiecesUseCase @Inject constructor(private val gameRepository: GameRepository) {

    operator fun invoke(): Either<ErrorApp, List<Piece>> = gameRepository.getPiecesState()

}