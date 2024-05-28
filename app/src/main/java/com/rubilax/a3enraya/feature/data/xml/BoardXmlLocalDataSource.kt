package com.rubilax.a3enraya.feature.data.xml

import android.content.Context
import com.google.gson.Gson
import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.app.left
import com.rubilax.a3enraya.app.right
import com.rubilax.a3enraya.feature.domain.BoardSquare
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoardXmlLocalDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPref = context.getSharedPreferences("Board", Context.MODE_PRIVATE)

    private val serializer = Gson()


    fun saveBoardSquares(boardSquare: BoardSquare): Either<ErrorApp, Boolean> {
        return try {
            with(sharedPref.edit()) {
                putString(
                    boardSquare.id.toString(),
                    serializer.toJson(boardSquare, boardSquare::class.java)
                )
                apply()
            }
            true.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }


    }

    fun saveBoard(piecesList: List<BoardSquare>) {
        piecesList.map {
            saveBoardSquares(it)
        }
    }

    fun getBoardSquares(): Either<ErrorApp, List<BoardSquare>> {
        return try {
            sharedPref.all.map {
                serializer.fromJson(it.value as String, BoardSquare::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }
    }

    fun cleanBoard(): Either<ErrorApp, Boolean> {
        return try {
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            true.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }
    }
}