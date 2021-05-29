package com.laksana.themovies.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.laksana.themovies.DetailActivity
import com.laksana.themovies.DetailActivity.Companion.EXTRA_DATA
import com.laksana.themovies.DetailActivity.Companion.EXTRA_TYPE
import com.laksana.themovies.DetailActivity.Companion.TYPE_MOVIES
import com.laksana.themovies.ui.adapter.MovieAdapter
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.databinding.FragmentMovieBinding
import com.laksana.themovies.viewmodel.ViewModelFactory
import com.laksana.themovies.vo.Status

class MovieFragment : Fragment(), MovieCallback {

    private lateinit var movieBinding: FragmentMovieBinding
    private val movieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return movieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            setRecyclerview()

            movieViewModel.getMovies().observe(viewLifecycleOwner, {
                it?.let {
                    when(it.status){
                        Status.LOADING ->
                            movieBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            movieBinding.progressBar.visibility = View.INVISIBLE
                            movieAdapter.submitList(it.data)
                            Log.d("fragment", it.data.toString())
                        }
                        Status.ERROR -> {
                            movieBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            movieBinding.recyclerviewMovie.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = movieAdapter
                //setHasFixedSize(true)
            }
        }
    }

    private fun setRecyclerview() {
        movieBinding.recyclerviewMovie.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    override fun onItemClicked(data: MovieEntity) {
        Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
        val moveViewDetail = Intent(context, DetailActivity::class.java)
        moveViewDetail.putExtra(EXTRA_DATA, data.movieId)
        moveViewDetail.putExtra(EXTRA_TYPE, TYPE_MOVIES)
        startActivity(moveViewDetail)
    }



}