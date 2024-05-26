package com.rubilax.a3enraya.feature.data

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.app.left
import com.rubilax.a3enraya.app.right
import com.rubilax.a3enraya.feature.data.xml.BoardXmlLocalDataSource
import com.rubilax.a3enraya.feature.data.xml.PieceXmlLocalDataSource
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.GameRepository
import com.rubilax.a3enraya.feature.domain.Piece
import javax.inject.Inject

class GameDataRepository @Inject constructor(
    private val pieceXmlLocalDataSource: PieceXmlLocalDataSource,
    private val boardXmlLocalDataSource: BoardXmlLocalDataSource
): GameRepository {
    override fun getPiecesState(): Either<ErrorApp, List<Piece>> {
        val localPieces = pieceXmlLocalDataSource.getPieces()
        return if (localPieces.isLeft() || localPieces.get().isEmpty()) {
            val pieceNull = Piece(0, 0)
            val piece1 = Piece(1, 1)
            val piece2 = Piece(2, 2)

            val piecesList = listOf(pieceNull, piece1, piece2)

            pieceXmlLocalDataSource.savePieces(piecesList)
            piecesList.right()

        } else {
            localPieces
        }
    }

    override fun getBoard(): Either<ErrorApp, List<BoardSquare>> {
        val localBoard = boardXmlLocalDataSource.getBoardSquares()
        return if (localBoard.isLeft() || localBoard.get().isEmpty()){
            val boardSquares = mutableListOf<BoardSquare>()
            for (i in 0..8) {
                val boardSquare = BoardSquare(id = i, type = 0)
                boardSquares.add(boardSquare)
            }
            boardXmlLocalDataSource.saveBoard(boardSquares)
            boardSquares.right()
        } else{
            localBoard
        }

    }

    override fun cleanBoard(): Either<ErrorApp, Boolean> {
        return try {
            boardXmlLocalDataSource.cleanBoard()
            true.right()
        } catch (ex: Exception){
            ErrorApp.DataError.left()
        }
    }


}