package com.example.bestmovie2.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MainReceiver : BroadcastReceiver() {

    companion object {
        const val MOVIE_LOADED = "MOVIE_LOADED"
        const val MOVIE_UNLOADED = "MOVIE_UNLOADED"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("MainReceiver", "onReceive $intent")

        when (intent.action) {
            MOVIE_LOADED -> RepositoryImpl.movieLoaded(intent.extras?.getParcelable<Movie>("MOVIE_EXTRA"))
            MOVIE_UNLOADED -> RepositoryImpl.movieLoaded(null)
        }
    }
}