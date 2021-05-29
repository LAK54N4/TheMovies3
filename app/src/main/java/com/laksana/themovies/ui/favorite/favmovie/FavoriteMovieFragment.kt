package com.laksana.themovies.ui.favorite.favmovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.laksana.themovies.DetailActivity
import com.laksana.themovies.R
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.databinding.FavoriteEmptyBinding
import com.laksana.themovies.databinding.FavoriteMovieFragmentBinding
import com.laksana.themovies.ui.adapter.MovieAdapter
import com.laksana.themovies.ui.favorite.FavoritesViewModel
import com.laksana.themovies.ui.movie.MovieCallback
import com.laksana.themovies.utils.Constants.ASC
import com.laksana.themovies.utils.Constants.DEFAULT
import com.laksana.themovies.utils.Constants.DESC
import com.laksana.themovies.viewmodel.ViewModelFactory

class FavoriteMovieFragment : Fragment(), MovieCallback {

    companion object {
        fun newInstance() = FavoriteMovieFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    private lateinit var binding: FavoriteMovieFragmentBinding
    private lateinit var emptyBinding: FavoriteEmptyBinding

    private var movieAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteMovieFragmentBinding.inflate(inflater,container, false)
        emptyBinding = FavoriteEmptyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerview()
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewMovieFavorite)

        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]

        val customList = listOf(DEFAULT, ASC, DESC)
        val customAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                customList
        )

        with(binding.actionBarSpinner) {
            adapter = customAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Toast.makeText(
                            requireContext(),
                            "${parent?.getItemAtPosition(position).toString()} selected",
                            Toast.LENGTH_SHORT
                    ).show()
                    showListMovie(parent?.getItemAtPosition(position).toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    showListMovie(customList[0])
                }
            }
        }
    }

    private fun showListMovie(s: String) {

        viewModel.getFavoriteListMovie(s).observe(this, {
            binding.progressBar.visibility = View.INVISIBLE
            movieAdapter.submitList(it)
        })
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val swipedPosition = viewHolder.adapterPosition
    val movieEntity = movieAdapter.getSwipedData(swipedPosition)
        movieEntity?.let {
            viewModel.setMovie(it)
        }
    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
        .setAction(R.string.message_ok) {
            movieEntity?.let {
                viewModel.setMovie(it)
            }
        }.show()
    }

    })

    private fun setRecyclerview() {
        binding.recyclerviewMovieFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
    }

    override fun onItemClicked(data: MovieEntity) {
        Toast.makeText(requireContext(), data.title, Toast.LENGTH_SHORT).show()
        val moveViewDetail = Intent(context, DetailActivity::class.java)
        moveViewDetail.putExtra(DetailActivity.EXTRA_DATA, data.movieId)
        moveViewDetail.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE_MOVIES)
        startActivity(moveViewDetail)
    }
}