package com.example.moviekotlinapp.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviekotlinapp.R
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: ArrayList<MovieLocal> = ArrayList()
    private lateinit var onItemClick: OnItemMovieClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie, parent, false
        )

        return MovieViewHolder(itemMovieBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.itemMovieBinding.movie = movie

        Glide.with(holder.itemMovieBinding.root)
            .load(movie.images)
            .centerCrop()
            .placeholder(R.drawable.image_placeholder)
            .into(holder.itemMovieBinding.moviePosterImg)

        holder.itemMovieBinding.root.setOnClickListener {
            onItemClick.onClickListener(movieList[position])
        }
    }

    fun setMovies(movieList: ArrayList<MovieLocal>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    fun onItemClicked(onItemClick: OnItemMovieClicked) {
        this.onItemClick = onItemClick
    }

    class MovieViewHolder(val itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root)

    interface OnItemMovieClicked {
        fun onClickListener(movie: MovieLocal)
    }
}