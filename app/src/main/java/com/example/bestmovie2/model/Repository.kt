package com.example.bestmovie2.model

interface Repository {
    fun getMovieFromServer(): Movie?

    fun getMovieFromLocalStorageRus(): List<Movie>

    fun getWeatherFromLocalStorageWorld(): List<Movie>

    fun movieLoaded(movie: Movie?)
    fun addLoadedListener(listener: OnLoadListener)
    fun removeLoadedListener(listener: OnLoadListener)

    fun interface OnLoadListener {
        fun onLoaded()
    }
}