package com.rubilax.a3enraya.feature.domain

data class Turn(
    var player: String,
)

data class BoardSquare(
    val id: Int,
    var type: Int
)
