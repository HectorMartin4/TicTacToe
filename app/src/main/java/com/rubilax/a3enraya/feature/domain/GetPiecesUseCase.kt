package com.rubilax.a3enraya.feature.domain

import javax.inject.Inject

class GetPiecesUseCase @Inject constructor(private val gameRepository: GameRepository) {

    operator fun invoke(): List<Piece> = gameRepository.getPiecesState()

}