package com.example.networkduo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.example.networkduo.R
import com.example.networkduo.data.Movie
import com.example.networkduo.data.network.TmdbService
import com.example.networkduo.readableFormat
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.movie_title

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val id: Long = MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = MovieDetailViewModelFactory(id, activity!!.application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }


    private fun setData(movie: Movie){
        //TODO setup all data
//IMAGE and context is activity
        Glide.with(activity!!)
            .load(TmdbService.POSTER_BASE_URL+movie.posterPath)
            .error(R.drawable.poster_placeholder)
            .into(movie_poster)

        Glide.with(activity!!)
            .load(TmdbService.BACKdROP_BASE_URL+movie.backdropPath)
            .into(movie_backdrop)


        movie_title.text = movie.title
        movie_overview.text = movie.overview

        movie_release_date.text = movie.releaseDate.readableFormat()
    }
}
