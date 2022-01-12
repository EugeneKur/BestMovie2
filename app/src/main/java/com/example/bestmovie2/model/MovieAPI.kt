package com.example.bestmovie2.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface MovieAPI {

    @GET("3/movie/{id}")
    fun getMovie (
        @Path("id") id: String,
        @Query("api_key") token: String

    ): Call<MovieDTO>
}