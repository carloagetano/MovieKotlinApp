package com.example.moviekotlinapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moviekotlinapp.data.database.entity.MovieLocal

@Dao
interface MovieLocalDao {

    //adds a new entry to database
    //if the data is same/conflict, it'll be replaced by the new data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieLocal: MovieLocal)

    //read all movies from database and arrange in asc order by id
    @Query("SELECT * FROM movie_local order by id ASC")
    fun getAllMovies() : LiveData<List<MovieLocal>>

    //delete a movie
    @Delete
    suspend fun deleteMovie(movieLocal: MovieLocal)

    //update a movie
    @Update
    suspend fun updateMovie(movieLocal: MovieLocal)

    //delete all movies
    @Query("DELETE FROM movie_local")
    suspend fun clearMovies()

    //delete a movie by id
    @Query("DELETE FROM movie_local WHERE id = :id")
    suspend fun deleteMovieById(id: Int)
}