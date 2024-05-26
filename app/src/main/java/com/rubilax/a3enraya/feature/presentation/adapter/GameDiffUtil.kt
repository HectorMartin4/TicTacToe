package com.rubilax.a3enraya.feature.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.Piece

class GameDiffUtil : DiffUtil.ItemCallback<BoardSquare>() {
    override fun areItemsTheSame(oldItem: BoardSquare, newItem: BoardSquare): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BoardSquare, newItem: BoardSquare): Boolean {
        return oldItem == newItem
    }
}