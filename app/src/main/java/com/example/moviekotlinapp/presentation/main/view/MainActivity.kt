package com.example.moviekotlinapp.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviekotlinapp.R
import com.example.moviekotlinapp.data.database.entity.MovieLocal
import com.example.moviekotlinapp.data.model.Movie
import com.example.moviekotlinapp.databinding.ActivityMainBinding
import com.example.moviekotlinapp.presentation.main.adapter.MovieAdapter
import com.example.moviekotlinapp.presentation.main.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.lifecycleOwner = this

        subscribe()
    }

    private fun subscribe() {
        /*viewModel.getMoviesLiveData().observe(this) {
            if (!it.isNullOrEmpty()) {
                Log.d("Movie Status", "Observer getMovies: $it")
                viewModel.getAllMovies(it)
            }
        }*/

        viewModel.allMovies.observe(this) {
            Log.d("Movie Status", "Observer allMovies: $it")
            if (it.isNullOrEmpty()) {
                Log.d("Movie Status", "Observer allMovies is null: $it")
                viewModel.getMoviesLiveData()
            } else {
                loadRv(ArrayList(it))
            }
        }
    }

    private fun loadRv(movies: ArrayList<MovieLocal>) {

        binding.movieRv.layoutManager = LinearLayoutManager(this)
        binding.movieRv.setHasFixedSize(true)

        moviesAdapter = MovieAdapter()
        binding.movieRv.adapter = moviesAdapter

        moviesAdapter.setMovies(movies)

        moviesAdapter.onItemClicked(object : MovieAdapter.OnItemMovieClicked {
            override fun onClickListener(movie: MovieLocal) {
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra(MovieDetailsActivity.MOVIE, movie)
                startActivity(intent)
            }
        })
    }
}