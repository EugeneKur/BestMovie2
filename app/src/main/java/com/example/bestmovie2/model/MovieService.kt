package com.example.bestmovie2.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MovieService : Service() {

    companion object {
        const val  TAG = "MovieService"
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand $intent")
        Log.d(TAG, "current thread : " + Thread.currentThread().name)
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }


}