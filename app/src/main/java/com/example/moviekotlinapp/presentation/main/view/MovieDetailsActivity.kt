package com.example.moviekotlinapp.presentation.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.moviekotlinapp.R
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    companion object {
        const val MOVIE = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        binding.lifecycleOwner = this

        val movie = intent.getParcelableExtra<MovieLocal>(MOVIE)
        movie?.let { setMovieData(it) }

        onClickListeners()
    }

    private fun setMovieData(movie: MovieLocal) {
        binding.movie = movie

        //Set movie poster image
        Glide.with(this)
            .load(movie.images)
            .centerCrop()
            .into(binding.moviePosterImg)

        //Set movie BG image
        Glide.with(this)
            .load(movie.poster)
            .centerCrop()
            .into(binding.movieBgPosterImg)
    }

    private fun onClickListeners() {
        binding.movieDetailTitleTv.setOnClickListener {
            finish()
        }
    }
}