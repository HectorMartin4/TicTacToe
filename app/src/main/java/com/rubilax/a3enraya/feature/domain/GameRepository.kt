package com.rubilax.a3enraya.feature.domain

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp

interface GameRepository {

    fun getBoard(): Either<ErrorApp, List<BoardSquare>>

    fun cleanBoard(): Either<ErrorApp, Boolean>

    fun getTurn(): Either<ErrorApp, Turn>

    fun cleanTurn(): Either<ErrorApp, Boolean>
}