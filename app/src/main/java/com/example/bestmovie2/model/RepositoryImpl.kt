package com.example.bestmovie2.model

object RepositoryImpl : Repository {

    private val listeners: MutableList<Repository.OnLoadListener> = mutableListOf()
    private  var movie: Movie? = null

    override fun getMovieFromServer(): Movie? = movie

    override fun getMovieFromLocalStorageRus(): List<Movie> = getRussianMovies()

    override fun getWeatherFromLocalStorageWorld(): List<Movie> = getWorldMovies()

    override fun movieLoaded(movie: Movie?) {
        this.movie = movie

        listeners.forEach { it.onLoaded()}
    }

    override fun addLoadedListener(listener: Repository.OnLoadListener) {
        listeners.add(listener)
    }

    override fun removeLoadedListener(listener: Repository.OnLoadListener) {
        listeners.remove(listener)
    }


}