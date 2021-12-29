package com.example.bestmovie2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestmovie2.model.Repository
import com.example.bestmovie2.model.RepositoryImpl
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val  liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getMovie() {
        liveDataToObserve.value = AppState.Loading

        Thread {
            Thread.sleep(2000)

            if (Random.nextBoolean()) {
                val movie = repo.getMovieFromServer()
                liveDataToObserve.postValue(AppState.Success(movie))
            } else {
                liveDataToObserve.postValue(AppState.Error(Exception("Проверьте интрнет")))
            }

        }.start()
    }
}