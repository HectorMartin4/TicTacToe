package com.rubilax.a3enraya.feature.data.xml

import android.content.Context
import com.rubilax.a3enraya.app.Either
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.app.left
import com.rubilax.a3enraya.app.right
import com.rubilax.a3enraya.feature.domain.Turn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TurnXmlLocalDataSource @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPref = context.getSharedPreferences("Turn", Context.MODE_PRIVATE)
    fun saveTurn(turn: Turn): Either<ErrorApp, Boolean> {
        return try {
            with(sharedPref.edit()) {
                putString("turn", turn.player)
                apply()

            }
            true.right()
        } catch (ex: Exception) {
            ErrorApp.DataError.left()
        }
    }

    fun getTurn(): Either<ErrorApp, Turn> {
        return try {
            Turn(sharedPref.getString("turn", null)!!).right()
        } catch (ex:Exception){
            ErrorApp.DataError.left()
        }
    }

    fun clearTurn(): Either<ErrorApp, Boolean>{
        return try {
            with(sharedPref.edit()){
                clear()
                apply()
            }
            true.right()
        } catch (ex:Exception){
            ErrorApp.DataError.left()
        }
    }
}