package com.laksana.themovies.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.laksana.themovies.data.local.entity.MovieEntity
import com.laksana.themovies.databinding.ItemRecyclerviewBinding
import com.laksana.themovies.ui.movie.MovieCallback
import com.laksana.themovies.utils.Constants.IMAGE_ENDPOINT
import com.laksana.themovies.utils.Constants.POSTER_SIZE_W185
import com.laksana.themovies.utils.Constants.setImage

class MovieAdapter (private var callback: MovieCallback) : PagedListAdapter<MovieEntity, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
        Log.d("adapter1", movie.toString())
    }

    inner class ViewHolder (private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding){
                tvTitle.text = movie.title
                tvRelease.text = movie.movieRelease

                with(itemView) {
                    setImage(context, IMAGE_ENDPOINT+POSTER_SIZE_W185+ movie.moviePoster,
                        poster)
                }

                root.setOnClickListener {
                    callback.onItemClicked(movie)}
            }
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)
}
