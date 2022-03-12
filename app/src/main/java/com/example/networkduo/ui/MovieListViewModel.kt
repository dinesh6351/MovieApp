package com.example.networkduo.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.networkduo.data.Movie
import com.example.networkduo.data.MovieListRepository
import com.example.networkduo.data.network.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieListViewModel(application: Application): AndroidViewModel(application){
    private val repo: MovieListRepository =
        MovieListRepository(application)

    val movies: LiveData<List<Movie>> = repo.getMovies()

    //Error start
    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus:LiveData<LoadingStatus>
    get() = _loadingStatus

    //end
//    fun fetchFromNetwork(){
//        viewModelScope.launch {
//            withContext(Dispatchers.IO){
//                repo.fetchFromNetwork()
//            }
//
//        }
//    }

    fun fetchFromNetwork(){
        _loadingStatus.value= LoadingStatus.loading()
        viewModelScope.launch {
           _loadingStatus.value = withContext(Dispatchers.IO) {
               delay(300)
               repo.fetchFromNetwork()
           }
        }
    }

    fun refreshData(){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAllData()
        }
        fetchFromNetwork()
    }
}