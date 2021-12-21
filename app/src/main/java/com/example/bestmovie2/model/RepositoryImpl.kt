package com.example.bestmovie2.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie = Movie()

    override fun getMovieFromLocalStorageRus(): List<Movie> = getRussianMovies()

    override fun getWeatherFromLocalStorageWorld(): List<Movie> = getWorldMovies()

}