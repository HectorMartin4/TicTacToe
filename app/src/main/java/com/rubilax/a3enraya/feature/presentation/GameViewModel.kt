package com.rubilax.a3enraya.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.CleanBoardUseCase
import com.rubilax.a3enraya.feature.domain.GetBoardSquaresUseCase
import com.rubilax.a3enraya.feature.domain.GetTurnUseCase
import com.rubilax.a3enraya.feature.domain.SetPieceUseCase
import com.rubilax.a3enraya.feature.domain.Turn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getBoardSquaresUseCase: GetBoardSquaresUseCase,
    private val getTurnUseCase: GetTurnUseCase,
    private val setPieceUseCase: SetPieceUseCase,
    private val cleanBoardUseCase: CleanBoardUseCase
) : ViewModel() {

    private var currentUiState = UiState()
    private val _uiState = MutableLiveData(currentUiState)
    val uiState: LiveData<UiState> = _uiState


    fun setPiece(boardSquare: BoardSquare) {
        viewModelScope.launch(Dispatchers.IO) {
            setPieceUseCase.invoke(boardSquare).fold(
                { responseError(it) },
                { responseSuccess(it) }
            )
        }
    }

    private fun responseSuccess(success: Boolean) {
        currentUiState = currentUiState.copy(success = success)
        _uiState.postValue(currentUiState)
    }

    fun loadBoard(){
        viewModelScope.launch(Dispatchers.IO) {
            getBoardSquaresUseCase.invoke().fold(
                { responseError(it) },
                { responseSuccessBoard(it) }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp){
        currentUiState = currentUiState.copy(errorApp = errorApp)
        _uiState.postValue(currentUiState)
    }

    private fun responseSuccessBoard(board: List<BoardSquare>){
        currentUiState = currentUiState.copy(boardSquares = board)
        _uiState.postValue(currentUiState)
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
        currentUiState = currentUiState.copy(turn = turn)
        _uiState.postValue(currentUiState)
    }


    fun cleanBoard() {
        viewModelScope.launch(Dispatchers.IO) {
            cleanBoardUseCase.invoke().fold(
                { responseError(it) },
                { responseSuccess(it) }
            )
        }
    }

    data class UiState(
        val turn: Turn? = null,
        val errorApp: ErrorApp? = null,
        val boardSquares: List<BoardSquare> = emptyList(),
        val success: Boolean? = null
    )
}