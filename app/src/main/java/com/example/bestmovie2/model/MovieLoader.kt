package com.example.bestmovie2.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object MovieLoader {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    private val movieAPI: MovieAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(MovieAPI::class.java)

    private val YOUR_API_KEY: String = "5134723d747613eb7f9cbdefa210bed4"

    fun load(movie: Movie, listener: OnMovieLoadListener) {

        var urlConnection: HttpsURLConnection? = null


        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${movie.title.id}?api_key=5134723d747613eb7f9cbdefa210bed4")

            urlConnection = uri.openConnection() as HttpsURLConnection
            // не понял как этим пользоваться, у меня ключ сразу URL urlConnection.addRequestProperty("key","eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MTM0NzIzZDc0NzYxM2ViN2Y5Y2JkZWZhMjEwYmVkNCIsInN1YiI6IjYxYzIyNDhlMjE2MjFkMDA5NTkyYTUxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.mqWEl-5jAPjG3_64LV_ILFtVCYrn_ZHZnEoTP9dQtdo")
            urlConnection.requestMethod = "GET" // установка метода получения данных -- GET
            urlConnection.readTimeout = 1000 // установка таймаута -- 1 000 миллисекунд
            urlConnection.connectTimeout = 1000
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream)) // читаем  данные в поток
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                reader.lines().collect(Collectors.joining("\n"))
            } else {
                ""
            }

            Log.d("DEBUGLOG", "result: $result")

            val movieDTO = Gson().fromJson(result, MovieDTO::class.java)

            listener.onLoaded(movieDTO)

        } catch (e: Exception) {
            listener.onFailed(e)
            Log.e("DEBUGLOG", "Fail connection", e)
        } finally {
            urlConnection?.disconnect()
        }

    }

    fun loadOkHttp (movie: Movie, listener: OnMovieLoadListener) {
        val request : Request = Request.Builder()
            .get()
            .addHeader("api_key", YOUR_API_KEY)
            .url("https://api.themoviedb.org/3/movie/${movie.title.id}?api_key=5134723d747613eb7f9cbdefa210bed4")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                listener.onFailed(e)
                Log.e("DEBUGLOG", "Fail connection", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val movieDTO = Gson().fromJson(response.body?.string(), MovieDTO::class.java)
                    listener.onLoaded(movieDTO)
                } else {
                    listener.onFailed(Exception(response.body?.string()))
                    Log.e("DEBUGLOG", "Fail connection $response")
                }
            }
        })

    }

    fun loadRetrofit (movie: Movie, listener: OnMovieLoadListener) {

        movieAPI.getMovie(movie.title.id.toString(), YOUR_API_KEY)
            .enqueue(object :retrofit2.Callback<MovieDTO>{
                override fun onResponse(
                    call: retrofit2.Call<MovieDTO>,
                    response: retrofit2.Response<MovieDTO>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { listener.onLoaded(it) }
                    } else {
                        listener.onFailed(Exception(response.message()))
                        Log.e("DEBUGLOG", "Fail connection $response")
                    }
                }

                override fun onFailure(call: retrofit2.Call<MovieDTO>, t: Throwable) {
                    listener.onFailed(t)
                }

            })

    }

    interface OnMovieLoadListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }

}