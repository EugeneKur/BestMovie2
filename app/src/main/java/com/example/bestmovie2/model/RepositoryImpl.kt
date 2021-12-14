package com.example.bestmovie2.model

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Movie {
        return Movie()
    }

    override fun getWeatherFromLocalStorage(): Movie {
        return Movie()
    }

}