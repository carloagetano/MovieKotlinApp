package com.example.moviekotlinapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviekotlinapp.data.database.MovieLocalDao
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.data.service.RetrofitInstance
import com.example.moviekotlinapp.data.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MovieRepository(private val movieLocalDao: MovieLocalDao) {

    private val moviesLiveData = MutableLiveData<ArrayList<MovieLocal>>()

    fun getMoviesLiveData(): LiveData<ArrayList<MovieLocal>> {
        return moviesLiveData
    }

    suspend fun loadData() = withContext(Dispatchers.IO) {
        val movieList = movieLocalDao.getAllMovies()

        if (movieList.isEmpty()) {
            Log.d("Movie Repository", "Loading from API")
            //load from API
            fetchFromAPI()
        } else {
            Log.d("Movie Repository", "Loading from database")
            //load from database
            moviesLiveData.postValue(ArrayList(movieList))
        }
    }

    //fetch data from API using retrofit
    private fun fetchFromAPI() = runBlocking {

        val response = RetrofitInstance.service.getMovies()

        if (response.isSuccessful) {
            val convertedMovie = response.body()?.let { convertMovie(it) }

            convertedMovie?.let {
                saveToDatabase(it)
            }

        } else {
            Log.d("Movie Repository", "Error found is " + response.message())
        }
    }

    //save to database and pass data from database to livedata
    private suspend fun saveToDatabase(movies: ArrayList<MovieLocal>) =
        withContext(Dispatchers.IO) {
            movies.forEach { movieLocal -> insertMovie(movieLocal) }

            moviesLiveData.postValue(ArrayList(getAllMovies()))
            Log.d("Movie Repository", "Saved to database and displaying data")
        }

    //post movie to api then add movie from response to database
    suspend fun addMovie(movie: Movie, callback: (response: Boolean) -> Unit) = withContext(Dispatchers.IO) {
        val response = RetrofitInstance.service.addMovie(movie, Constants.AUTH_TOKEN)

        if (response.isSuccessful) {
            val addedMovie = response.body()

            if (addedMovie != null) {
                val convertedMovie = MovieLocal(
                    null, addedMovie.title, addedMovie.year,
                    addedMovie.rated, addedMovie.released, addedMovie.runtime,
                    addedMovie.genre, addedMovie.director, addedMovie.writer,
                    addedMovie.actors, addedMovie.plot, addedMovie.language, addedMovie.country,
                    addedMovie.awards, addedMovie.images[1], addedMovie.metascore,
                    addedMovie.imdbRating, addedMovie.imdbVotes, addedMovie.imdbID,
                    addedMovie.type, addedMovie.response, addedMovie.images[0],
                    addedMovie.totalSeasons, addedMovie.comingSoon
                )

                insertMovie(convertedMovie)
                callback.invoke(true)

            } else {
                Log.d("Movie Repository", "No response body")
            }

        } else {
            callback.invoke(false)
            Log.d("Movie Repository", "Error found is " + response.message())
        }
    }

    suspend fun reloadData() = withContext(Dispatchers.IO) {
        moviesLiveData.postValue(ArrayList(getAllMovies()))
    }

    //data from room/local database
    //get all movies
    private fun getAllMovies(): List<MovieLocal> = movieLocalDao.getAllMovies()

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

    private fun convertMovie(movieList: ArrayList<Movie>): ArrayList<MovieLocal> {
        val convertedMovieList = ArrayList<MovieLocal>()

        movieList.forEach { movie ->
            convertedMovieList.add(
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

        return convertedMovieList
    }
}