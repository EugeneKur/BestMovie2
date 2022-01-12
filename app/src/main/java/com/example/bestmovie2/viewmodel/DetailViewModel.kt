package com.example.bestmovie2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestmovie2.model.*
import com.example.bestmovie2.view.App
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val  liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun saveHistory(movie: Movie) {
        localRepo.saveEntity(movie)
    }

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