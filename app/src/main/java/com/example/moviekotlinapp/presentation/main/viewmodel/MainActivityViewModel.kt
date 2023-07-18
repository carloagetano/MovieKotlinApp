package com.example.moviekotlinapp.presentation.main.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviekotlinapp.data.database.MovieDatabase
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository
    val allMovies: LiveData<List<MovieLocal>>

    init {
        val movieLocalDao = MovieDatabase.getDatabase(application).getMovieLocalDao()
        movieRepository = MovieRepository(movieLocalDao)
        allMovies = movieRepository.getAllMovies()
        Log.d("Movie Status", "Init allMovies: " + allMovies.value.toString())
    }

    //fetch movies data from api
    fun getMoviesLiveData() {
        movieRepository.getMoviesLiveData().value?.let { getAllMovies(it) }
    }

    fun getAllMovies(movieList: ArrayList<Movie>) {
        if (allMovies.value.isNullOrEmpty()) {
            //fetch from api and insert movies to database
            Log.d("Movie Status", "inside allMovies: " + allMovies.value.toString())
            viewModelScope.launch(Dispatchers.IO) {
                movieList.forEach { movie ->
                    movieRepository.insertMovie(
                        MovieLocal(
                            null, movie.title, movie.year,
                            movie.rated, movie.released, movie.runtime,
                            movie.genre, movie.director, movie.writer,
                            movie.actors, movie.plot, movie.language, movie.country,
                            movie.awards, movie.images[1], movie.metascore,
                            movie.imdbRating, movie.imdbVotes, movie.imdbID,
                            movie.type, movie.response, movie.images[0],
                            movie.totalSeasons, movie.comingSoon
                        )
                    )
                }
            }
        }
    }
}