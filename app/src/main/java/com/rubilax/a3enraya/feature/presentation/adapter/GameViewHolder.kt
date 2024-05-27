package com.rubilax.a3enraya.feature.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rubilax.a3enraya.R
import com.rubilax.a3enraya.databinding.ViewItemPieceBinding
import com.rubilax.a3enraya.feature.domain.BoardSquare

class GameViewHolder(private val view: View): ViewHolder(view) {

    private lateinit var binding: ViewItemPieceBinding

    fun bind(boardSquare: BoardSquare, onClick: (BoardSquare) -> Unit) {
        binding = ViewItemPieceBinding.bind(view)

        binding.apply {
            when (boardSquare.type) {
                0 -> {
                    imgPiece.setColorFilter(R.color.white)
                }
                1 -> {
                    imgPiece.setImageResource(R.drawable.ic_cross)
                }
                2 -> {
                    imgPiece.setImageResource(R.drawable.ic_circle)
                }
            }
            view.setOnClickListener{
                onClick(boardSquare)
            }
        }
    }
}