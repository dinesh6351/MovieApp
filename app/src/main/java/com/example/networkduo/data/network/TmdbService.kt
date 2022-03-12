package com.example.networkduo.data.network

import com.example.networkduo.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TmdbService{

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL ="https://image.tmdb.org/t/p/w185"
        const val BACKdROP_BASE_URL ="https://image.tmdb.org/t/p/w300"

        private val retrofitService by lazy {

            // Add api key to every request
            // lasy means that code inside the Lamda will get executed only when somebody ask some where the code retrofitservices
//            each request pass to  base_url link api key and append every time add automatically
//            buildconfig add each time to request
            val interceptor = Interceptor { chain ->
                val request = chain.request()
                val url = request.url().newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMBD_API_KEY)
                    .build()
                val newRequest = request.newBuilder()
                    .url(url)
                    .build()
                chain.proceed(newRequest)

            }

            val httpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
//            convert GSon function
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
//            Retrofit understood automatically call build

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TmdbService::class.java)
            //itself

        }

        fun getInstance(): TmdbService {
            return retrofitService
        }
    }

    @GET("discover/movie?certification_country=US&adult=false&vote_count.gte=100&" +
            "with_original_language=en&sort_by=primary_release_date.desc")
//    @GET("discover/movie?api_key=<<api_key>>&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate")
    suspend fun getMovies(): Response<TmdbMovieList>

}