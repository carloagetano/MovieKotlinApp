package com.example.moviekotlinapp.presentation.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.moviekotlinapp.R
import com.example.moviekotlinapp.databinding.ActivityAddMovieBinding
import com.example.moviekotlinapp.presentation.main.viewmodel.AddMovieViewModel

class AddMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMovieBinding
    private lateinit var viewModel: AddMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_movie)
        viewModel = ViewModelProvider(this)[AddMovieViewModel::class.java]

        binding.lifecycleOwner = this

        subscribe()
        onClickListeners()
    }

    private fun subscribe() {
        viewModel.isMovieAdded.observe(this) {
            if (it) {
                clearFields()
            } else {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onClickListeners() {
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.addMovieBtn.setOnClickListener {
            val title = binding.titleEtv.text.toString()
            val released = binding.releasedEtv.text.toString()
            val rating = binding.ratingEtv.text.toString()
            val imgPoster = binding.imgPosterEtv.text.toString()
            val imgBackdrop = binding.imgBackdropEtv.text.toString()
            val plot = binding.plotEtv.text.toString()

            if (title.isNotEmpty() && released.isNotEmpty() && rating.isNotEmpty() && imgPoster.isNotEmpty()
                && imgBackdrop.isNotEmpty() && plot.isNotEmpty()
            ) {
                viewModel.addMovie(title, released, rating, imgPoster, imgBackdrop, plot)
            } else {
                Toast.makeText(this, "Please complete all fields.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFields() {
        binding.titleEtv.setText("")
        binding.releasedEtv.setText("")
        binding.ratingEtv.setText("")
        binding.imgPosterEtv.setText("")
        binding.imgBackdropEtv.setText("")
        binding.plotEtv.setText("")

        Toast.makeText(this, "Movie added successfully!", Toast.LENGTH_LONG).show()
    }
}