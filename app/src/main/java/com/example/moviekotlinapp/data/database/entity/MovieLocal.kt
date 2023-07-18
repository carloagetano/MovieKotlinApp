package com.example.moviekotlinapp.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_local")
data class MovieLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "year")
    val year: String?,

    @ColumnInfo(name = "rated")
    val rated: String?,

    @ColumnInfo(name = "released")
    val released: String?,

    @ColumnInfo(name = "runtime")
    val runtime: String?,

    @ColumnInfo(name = "genre")
    val genre: String?,

    @ColumnInfo(name = "director")
    val director: String?,

    @ColumnInfo(name = "writer")
    val writer: String?,

    @ColumnInfo(name = "actors")
    val actors: String?,

    @ColumnInfo(name = "plot")
    val plot: String?,

    @ColumnInfo(name = "language")
    val language: String?,

    @ColumnInfo(name = "country")
    val country: String?,

    @ColumnInfo(name = "awards")
    val awards: String?,

    @ColumnInfo(name = "poster")
    val poster: String?,

    @ColumnInfo(name = "metascore")
    val metascore: String?,

    @ColumnInfo(name = "imdb_rating")
    val imdbRating: String?,

    @ColumnInfo(name = "imdb_votes")
    val imdbVotes: String?,

    @ColumnInfo(name = "imdb_id")
    val imdbID: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "response")
    val response: String?,

    @ColumnInfo(name = "images")
    val images: String?,

    @ColumnInfo(name = "total_seasons")
    val totalSeasons: String?,

    @ColumnInfo(name = "coming_soon")
    val comingSoon: Boolean?
) : Parcelable
