package com.laksana.themovies.ui.tvshow

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
import com.laksana.themovies.DetailActivity.Companion.TYPE_TVSHOW
import com.laksana.themovies.data.local.entity.TvShowEntity
import com.laksana.themovies.databinding.FragmentTvshowBinding
import com.laksana.themovies.ui.adapter.TvShowAdapter
import com.laksana.themovies.viewmodel.ViewModelFactory
import com.laksana.themovies.vo.Status

class TvShowFragment : Fragment(), TvShowCallback {

    private lateinit var tvShowBinding: FragmentTvshowBinding
    private val tvShowAdapter = TvShowAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        tvShowBinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
        return tvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            setRecyclerview()

            viewModel.getTvShows().observe(viewLifecycleOwner, {
                it?.let {
                    when(it.status){
                        Status.LOADING -> tvShowBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            tvShowBinding.progressBar.visibility = View.INVISIBLE
                            tvShowAdapter.submitList(it.data)
                            Log.d("fragmentTv", it.data.toString())
                        }
                        Status.ERROR -> {
                            tvShowBinding.progressBar.visibility = View.VISIBLE
                            Toast.makeText(context, "Ada kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }


    private fun setRecyclerview() {
        tvShowBinding.recyclerviewTvShow.apply {
            layoutManager = LinearLayoutManager(context)
            //adapter = TvShowAdapter(this@TvShowFragment)
            adapter = tvShowAdapter
        }
    }

    override fun onItemClicked(data: TvShowEntity) {
        Toast.makeText(context, data.tvShowName, Toast.LENGTH_SHORT).show()
        val moveViewDetail = Intent(context, DetailActivity::class.java)
        moveViewDetail.putExtra(EXTRA_DATA, data.id)
        moveViewDetail.putExtra(EXTRA_TYPE, TYPE_TVSHOW)
        startActivity(moveViewDetail)
        tvShowBinding.progressBar.visibility = View.VISIBLE
    }
}