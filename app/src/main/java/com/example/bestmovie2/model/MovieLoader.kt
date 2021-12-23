package com.example.bestmovie2.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object MovieLoader {

    fun load(movie: Movie, listener: OnMovieLoadListener) {

        val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())
        Thread {
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

                handler.post {
                    listener.onLoaded(movieDTO)
                }

            } catch (e: Exception) {
                handler.post {
                    listener.onFailed(e)
                }
                Log.e("DEBUGLOG", "Fail connection", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()

    }

    interface OnMovieLoadListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable)
    }

}