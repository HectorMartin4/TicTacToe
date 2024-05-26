package com.rubilax.a3enraya.feature.domain

data class Piece(
    val id: Int,
    val type: Int
)

data class BoardSquare(
    val id: Int,
    var type: Int
)
