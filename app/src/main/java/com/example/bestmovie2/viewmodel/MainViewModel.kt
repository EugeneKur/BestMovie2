package com.example.bestmovie2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestmovie2.model.Repository
import com.example.bestmovie2.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val  liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl

    fun getData(): LiveData<AppState> = liveDataToObserve

    fun getMovieFromLocalStorageRus() = getDataFromLocalSource(true)

    fun getMovieFromLocalStorageWorld() = getDataFromLocalSource(false)

    fun getMovieFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean = true) {
        liveDataToObserve.value = AppState.Loading

        Thread {
            Thread.sleep(1000)


            val movie = if (isRussian) {
                repo.getMovieFromLocalStorageRus()
            } else {
                repo.getWeatherFromLocalStorageWorld()
            }
            liveDataToObserve.postValue(AppState.Success(movie))


        }.start()
    }

}