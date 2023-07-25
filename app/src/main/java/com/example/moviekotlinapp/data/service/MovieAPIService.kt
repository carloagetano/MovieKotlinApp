package com.example.moviekotlinapp.data.service

import com.example.moviekotlinapp.data.model.EncryptedMovie
import com.example.moviekotlinapp.data.model.Movie
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MovieAPIService {

    @GET("/movies")
    suspend fun getMovies(): Response<ArrayList<Movie>>

    @POST("/add-movie")
    suspend fun addMovie(
        @Body encryptedMovie: EncryptedMovie,
        @Header("Authorization") authorization: String
    ): Response<EncryptedMovie>
}