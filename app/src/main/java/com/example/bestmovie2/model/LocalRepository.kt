package com.example.bestmovie2.model

interface LocalRepository {

    fun getAllHistory(): List<Movie>

    fun saveEntity(movie: Movie)

}