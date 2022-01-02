package com.example.bestmovie2.model

import com.google.gson.annotations.SerializedName

data class MovieDTO (
    @SerializedName("overview") //Замена overview из полученных данных tmdb на about
    val about: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Float?,
    val poster_path: String?,
)