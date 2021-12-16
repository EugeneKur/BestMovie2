package com.example.bestmovie2.model

class RepositoryImpl : Repository {
    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorageRus(): List<Movie> {
        return getRussianMovies()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Movie> {
        return getWorldMovies()
    }


}