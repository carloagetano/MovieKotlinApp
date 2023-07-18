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

    init {
        val movieLocalDao = MovieDatabase.getDatabase(application).getMovieLocalDao()
        movieRepository = MovieRepository(movieLocalDao)

        viewModelScope.launch {
            movieRepository.loadData()
        }
    }

    //fetch movies data from repo
    fun getMoviesLiveData(): LiveData<ArrayList<MovieLocal>> {
        return movieRepository.getMoviesLiveData()
    }

    suspend fun reloadData() {
        movieRepository.reloadData()
    }
}