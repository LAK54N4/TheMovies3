package com.laksana.themovies.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.laksana.themovies.R
import com.laksana.themovies.databinding.FragmentFavoriteBinding
import com.laksana.themovies.ui.adapter.SectionPagerAdapter
import com.laksana.themovies.viewmodel.ViewModelFactory

class FavoritesFragment : Fragment() {

    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: FragmentStateAdapter

    lateinit var factory: ViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showTabs()

        factory = ViewModelFactory.getInstance(requireContext())
    }

    private fun showTabs() {
        viewPager2 = favoriteBinding.viewpager
        pagerAdapter = SectionPagerAdapter(this)
        viewPager2.adapter = pagerAdapter

        TabLayoutMediator(favoriteBinding.tabs, viewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> resources.getString(R.string.title_movie)
                else -> resources.getString(R.string.title_tvShow)
            }
        }.attach()
    }

}

