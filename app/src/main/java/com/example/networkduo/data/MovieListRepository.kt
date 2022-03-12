package com.example.networkduo.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.networkduo.data.network.ErrorCode
import com.example.networkduo.data.network.LoadingStatus
import com.example.networkduo.data.network.TmdbService
import java.lang.Exception

class MovieListRepository(context: Application){
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy { TmdbService.getInstance()}

    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    //error handling
    suspend fun fetchFromNetwork()=
//        { or =
//        val result = tmdbService.getMovies()
//        if(result.isSuccessful){
//                        body is TmdbMovies
//            val movieList = result.body()
//            movieList?.let{ movieListDao.insertMovies(it.results) }

        try {
            val result = tmdbService.getMovies()
        if(result.isSuccessful){
//                        body is TmdbMovies
            val movieList = result.body()
            movieList?.let{ movieListDao.insertMovies(it.results) }
            LoadingStatus.success()
        }else{
            LoadingStatus.error(
                ErrorCode.NO_DATA
            )
        }
        }catch (ex:Exception){
            LoadingStatus.error(
                ErrorCode.NETWORK_ERROR
            )
        }catch (ex:Exception){
            LoadingStatus.error(
                ErrorCode.UNKNOW_ERROR,ex.message
            )
        }
    suspend fun deleteAllData(){
        movieListDao.deleteAllData()
    }
    }