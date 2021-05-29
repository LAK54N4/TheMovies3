package com.laksana.themovies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.databinding.ItemRecyclerviewBinding
import com.laksana.themovies.ui.tvshow.TvShowCallback
import com.laksana.themovies.utils.Constants

class TvShowAdapter (private val callback: TvShowCallback): PagedListAdapter<TvShowEntity,TvShowAdapter.ViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder (private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.tvShowName
                tvRelease.text = tvShow.firstAirDate

                with(itemView) {
                    Constants.setImage(
                            context,
                            Constants.IMAGE_ENDPOINT+Constants.POSTER_SIZE_W185+tvShow.tvShowPoster,
                            binding.poster
                    )
                }

                root.setOnClickListener {
                    callback.onItemClicked(tvShow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tvShow = getItem(position)
        tvShow?.let {
            holder.bind(it) }

    }

    fun getSwipedData(swipedPosition: Int): TvShowEntity? = getItem(swipedPosition)
}