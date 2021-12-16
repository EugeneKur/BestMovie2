package com.example.bestmovie2.model

interface Repository {
    fun getMovieFromServer(): Movie

    fun getMovieFromLocalStorageRus(): List<Movie>

    fun getWeatherFromLocalStorageWorld(): List<Movie>
}