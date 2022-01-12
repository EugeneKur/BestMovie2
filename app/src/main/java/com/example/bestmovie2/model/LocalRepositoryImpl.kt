package com.example.bestmovie2.model

import java.util.*

class LocalRepositoryImpl(private val dao: HistoryDao): LocalRepository {
    override fun getAllHistory(): List<Movie> {
        return dao.all()
            .map { entity ->
                Movie(
                    title = Title(
                        name = entity.name,
                        rating = entity.rating.toFloat(),
                        year = entity.year,
                        id = entity.idDTO.toInt()
                    ),
                    about = entity.about,
                    note = entity.note,
                )
            }
    }

    override fun saveEntity(movie: Movie) {
        dao.insert(
            HistoryEntity(
                id = 0,
                idDTO = movie.title.id.toLong(),
                name = movie.title.name,
                about = movie.about,
                rating = movie.title.rating.toInt(),
                year = movie.title.year,
                timestamp = Date().time,
                note = movie.note
            )
        )
    }
}