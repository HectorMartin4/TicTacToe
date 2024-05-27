package com.rubilax.a3enraya.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.GetBoardSquaresUseCase
import com.rubilax.a3enraya.feature.domain.GetTurnUseCase
import com.rubilax.a3enraya.feature.domain.Turn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getBoardSquaresUseCase: GetBoardSquaresUseCase,
    private val getTurnUseCase: GetTurnUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadBoard(){
        viewModelScope.launch(Dispatchers.IO) {
            getBoardSquaresUseCase.invoke().fold(
                { responseError(it) },
                { responseSuccessBoard(it) }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp){
        _uiState.postValue(UiState(errorApp = errorApp))
    }

    private fun responseSuccessBoard(board: List<BoardSquare>){
        _uiState.postValue(UiState(boardSquares = board))
    }

    fun loadTurn(){
        viewModelScope.launch(Dispatchers.IO) {
            getTurnUseCase.invoke().fold(
                { responseError(it) },
                { responseSuccessTurn(it) }
            )
        }
    }

    private fun responseSuccessTurn(turn: Turn){
        _uiState.postValue(UiState(turn = turn))
    }


    data class UiState(
        val turn: Turn? = null,
        val errorApp: ErrorApp? = null,
        val boardSquares: List<BoardSquare> = emptyList()
    )
}