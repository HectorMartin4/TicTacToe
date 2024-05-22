package com.rubilax.a3enraya.feature.domain

interface GameRepository {

    fun savePiecesState()

    fun getPiecesState(): List<Piece>

    fun clearBoard()
}