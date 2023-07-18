package com.example.moviekotlinapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviekotlinapp.data.database.MovieLocalDao
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.data.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val movieLocalDao: MovieLocalDao) {

    private val moviesLiveData = MutableLiveData<ArrayList<Movie>>()

    //fetch data from API using retrofit
    fun getMoviesLiveData() : MutableLiveData<ArrayList<Movie>> {

        RetrofitInstance.service.getMovies().enqueue(object : Callback<ArrayList<Movie>> {
            override fun onResponse(
                call: Call<ArrayList<Movie>>,
                response: Response<ArrayList<Movie>>
            ) {
                if (response.body() != null) {
                    moviesLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
                Log.d("Movie Status", "Error found is " + t.message)
            }

        })

        return moviesLiveData
    }

    //data from room/local database
    //get all movies
    fun getAllMovies() : LiveData<List<MovieLocal>> = movieLocalDao.getAllMovies()

    //adds a movie to the database
    suspend fun insertMovie(movieLocal: MovieLocal) {
        movieLocalDao.insertMovie(movieLocal)
    }

    //deletes a movie from the database
    suspend fun deleteMovie(movieLocal: MovieLocal) {
        movieLocalDao.deleteMovie(movieLocal)
    }

    //updates a movie from the database
    suspend fun updateMovie(movieLocal: MovieLocal) {
        movieLocalDao.updateMovie(movieLocal)
    }

    //delete all movies from the database
    suspend fun clearMovies() {
        movieLocalDao.clearMovies()
    }
}