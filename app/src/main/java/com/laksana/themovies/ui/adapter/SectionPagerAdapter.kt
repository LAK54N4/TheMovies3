package com.laksana.themovies.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.laksana.themovies.ui.favorite.FavoritesFragment
import com.laksana.themovies.ui.favorite.favmovie.FavoriteMovieFragment
import com.laksana.themovies.ui.favorite.favtvshow.FavoriteTvShowFragment

class SectionPagerAdapter(favFragment: FavoritesFragment): FragmentStateAdapter(favFragment) {

    companion object {
        const val  NUM_PAGES = 2
    }

    override fun getItemCount(): Int {
        return  NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment.newInstance()
            1 -> FavoriteTvShowFragment.newInstance()
            else -> Fragment()
        }
    }

}