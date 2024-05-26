package com.rubilax.a3enraya.feature.domain

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp

interface GameRepository {

    fun getPiecesState(): Either<ErrorApp, List<Piece>>

    fun getBoard(): Either<ErrorApp, List<BoardSquare>>

    fun cleanBoard(): Either<ErrorApp, Boolean>
}