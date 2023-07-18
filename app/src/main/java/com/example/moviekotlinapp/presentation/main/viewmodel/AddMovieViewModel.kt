package com.example.moviekotlinapp.presentation.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviekotlinapp.data.database.MovieDatabase
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.data.repository.MovieRepository
import kotlinx.coroutines.launch

class AddMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository

    private val _isMovieAdded = MutableLiveData<Boolean>()

    val isMovieAdded: LiveData<Boolean>
        get() = _isMovieAdded

    init {
        val movieLocalDao = MovieDatabase.getDatabase(application).getMovieLocalDao()
        movieRepository = MovieRepository(movieLocalDao)
    }


    fun addMovie(
        title: String, released: String, rating: String, poster: String,
        backdrop: String, plot: String
    ) {

        val images: MutableList<String> = ArrayList()
        images.add(poster)
        images.add(backdrop)

        val newMovie = Movie(
            title, "", "", released, "", "",
            "", "", "", plot, "", "", "",
            "", "", rating, "", "", "",
            "", images, "", false
        )

        viewModelScope.launch {
            movieRepository.addMovie(newMovie) { response ->
                _isMovieAdded.postValue(response)
            }
        }
    }
}