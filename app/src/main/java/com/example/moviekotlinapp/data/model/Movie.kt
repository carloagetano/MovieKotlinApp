package com.example.moviekotlinapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Metascore") val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    @SerializedName("Type") val type: String?,
    @SerializedName("Response") val response: String?,
    @SerializedName("Images") val images: List<String>,
    val totalSeasons: String?,
    @SerializedName("ComingSoon") val comingSoon: Boolean?
) : Parcelable
