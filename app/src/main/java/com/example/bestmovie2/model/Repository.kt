package com.example.bestmovie2.model

interface Repository {
    fun getWeatherFromServer(): Movie

    fun getWeatherFromLocalStorage(): Movie
}