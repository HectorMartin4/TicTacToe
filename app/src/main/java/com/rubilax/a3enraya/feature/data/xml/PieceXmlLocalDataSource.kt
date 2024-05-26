package com.rubilax.a3enraya.feature.data.xml

import android.content.Context
import com.google.gson.Gson
import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.app.left
import com.rubilax.a3enraya.app.right
import com.rubilax.a3enraya.feature.domain.Piece
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PieceXmlLocalDataSource @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPref = context.getSharedPreferences("PiecesState", Context.MODE_PRIVATE)

    private val serializer = Gson()

    fun savePieces(piecesList: List<Piece>): Either<ErrorApp, Boolean> {

        return try {
            piecesList.map {
                with(sharedPref.edit()) {
                    putString(
                        it.id.toString(),
                        serializer.toJson(it, Piece::class.java)
                    )
                    apply()
                }
            }
            true.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }
    }


    fun getPieces(): Either<ErrorApp, List<Piece>> {
        return try {
            sharedPref.all.map {
                serializer.fromJson(it as String, Piece::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }
    }
    /*
        private fun deletePieces(): Either<ErrorApp, Boolean> {
             return try {
                with(sharedPref.edit()) {
                    clear()
                    apply()
                }
                true.right()
            } catch (ex: Exception){
                ErrorApp.DataError.left()
            }
        }
        */
}