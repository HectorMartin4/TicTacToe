package com.rubilax.a3enraya.feature.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubilax.a3enraya.app.ErrorApp
import com.rubilax.a3enraya.feature.domain.BoardSquare
import com.rubilax.a3enraya.feature.domain.GetBoardSquaresUseCase
import com.rubilax.a3enraya.feature.domain.GetPiecesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getBoardSquaresUseCase: GetBoardSquaresUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadBoard(){
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getBoardSquaresUseCase.invoke().fold(
                { responseError(it) },
                { responseSuccess(it) }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp){
        _uiState.postValue(UiState(errorApp = errorApp))
    }

    private fun responseSuccess(board: List<BoardSquare>){
        _uiState.postValue(UiState(boardSquares = board))
    }



    data class UiState(
        val isLoading: Boolean = false,
        val errorApp: ErrorApp? = null,
        val boardSquares: List<BoardSquare> = emptyList()
    )
}