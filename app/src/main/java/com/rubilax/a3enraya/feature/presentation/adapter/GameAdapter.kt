package com.rubilax.a3enraya.feature.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rubilax.a3enraya.R
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.Piece

class GameAdapter : ListAdapter<BoardSquare, GameViewHolder>(GameDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_piece, parent, false)

        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}