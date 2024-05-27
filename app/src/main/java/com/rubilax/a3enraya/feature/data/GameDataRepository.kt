package com.rubilax.a3enraya.feature.data

import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.app.left
import com.rubilax.a3enraya.app.right
import com.rubilax.a3enraya.feature.data.xml.BoardXmlLocalDataSource
import com.rubilax.a3enraya.feature.data.xml.TurnXmlLocalDataSource
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.GameRepository
import com.rubilax.a3enraya.feature.domain.Turn
import javax.inject.Inject

class GameDataRepository @Inject constructor(
    private val boardXmlLocalDataSource: BoardXmlLocalDataSource,
    private val turnXmlLocalDataSource: TurnXmlLocalDataSource
): GameRepository {

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

    override fun getTurn(): Either<ErrorApp, Turn> {
        return try {
            val localTurn = turnXmlLocalDataSource.getTurn()
            if (localTurn.isLeft()){
                val firstTurn = Turn("cross")
                turnXmlLocalDataSource.saveTurn(firstTurn)

                firstTurn.right()
            } else {
                localTurn
            }
        } catch (ex:Exception){
            ErrorApp.DataError.left()
        }
    }

    override fun cleanTurn(): Either<ErrorApp, Boolean> {
        return try {
            turnXmlLocalDataSource.clearTurn()
            true.right()
        } catch (ex:Exception){
            ErrorApp.DataError.left()
        }
    }
}