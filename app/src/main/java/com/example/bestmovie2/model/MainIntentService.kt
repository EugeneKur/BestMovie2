package com.example.bestmovie2.model

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log


class MainIntentService : IntentService("MainIntentService") {

    companion object {
        const val  TAG = "MainIntentService"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onCreate" + Thread.currentThread().name)

        Thread.sleep(2000)

        intent?.getParcelableExtra<Movie>("MOVIE_EXTRA")?.let {movie ->
            MovieLoader.load(movie, object : MovieLoader.OnMovieLoadListener {
                override fun onLoaded(movieDTO: MovieDTO) {
                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {

                        action = MainReceiver.MOVIE_LOADED
                        putExtra("MOVIE_EXTRA", Movie(
                            about = movieDTO.about ?: " ",
                            title = Title(movieDTO.title ?: " ", movieDTO.vote_average ?: 0f, movieDTO.release_date ?: "2000")
                        ))
                    })
                }

                override fun onFailed(throwable: Throwable) {

                    applicationContext.sendBroadcast(Intent(applicationContext, MainReceiver::class.java).apply {
                        action = MainReceiver.MOVIE_UNLOADED
                    })
                }

            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand $intent")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}