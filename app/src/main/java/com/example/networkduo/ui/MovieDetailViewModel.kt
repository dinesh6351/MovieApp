package com.example.networkduo.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.networkduo.data.Movie
import com.example.networkduo.data.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel(id: Long, application: Application): ViewModel(){
    private val repo: MovieDetailRepository =
        MovieDetailRepository(application)

    val movie: LiveData<Movie> =
            repo.getMovie(id)
}