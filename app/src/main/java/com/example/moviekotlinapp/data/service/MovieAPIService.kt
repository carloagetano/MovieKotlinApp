package com.example.moviekotlinapp.data.service

import com.example.moviekotlinapp.data.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieAPIService {

    @GET("/movies")
    fun getMovies() : Call<ArrayList<Movie>>
}