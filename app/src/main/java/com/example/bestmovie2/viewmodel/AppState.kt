package com.example.bestmovie2.viewmodel

import com.example.bestmovie2.model.Movie

sealed class AppState {
    data class Success<T>(val data: T) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

