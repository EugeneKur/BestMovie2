package com.example.bestmovie2.model

import android.content.Context
import androidx.work.*

class MainWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {

        var result = Result.success()

        MovieLoader.load(movie = Movie(), object : MovieLoader.OnMovieLoadListener{
            override fun onLoaded(movieDTO: MovieDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })

        return result
    }

    companion object {

        fun startWorker(context: Context) {
            val upLoadWorkRequest: WorkRequest =
                OneTimeWorkRequest.Builder(MainWorker::class.java)
                    .setConstraints(Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                    .build()
            WorkManager.getInstance(context).enqueue(upLoadWorkRequest)
        }
    }
}