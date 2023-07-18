package com.example.moviekotlinapp.data.service

import com.example.moviekotlinapp.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface MovieAPIService {

    @GET("/movies")
    suspend fun getMovies(): Response<ArrayList<Movie>>
}