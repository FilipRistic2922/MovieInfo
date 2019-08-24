package fr97.movieinfo.feature.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import fr97.movieinfo.feature.movielist.MovieListFragment
import java.lang.IllegalStateException

class TabPageAdapter(fragmentManager: FragmentManager, tabsCount: Int) :
    FragmentStatePagerAdapter(fragmentManager, tabsCount) {

//    private val fragments: List<Fragment> = listOf(
//        MovieListFragment.newInstance("popular"),
//        MovieListFragment.newInstance("top_rated"),
//        MovieListFragment.newInstance("upcoming")
//    )
    private val titles: List<String> = listOf("Popular", "Top Rated", "Upcoming")

    override fun getItem(position: Int): Fragment {
        Log.d("WTFF", "Position $position")
//        return fragments[position]
        return when (position) {
            0 -> MovieListFragment.newInstance("popular")
            1 -> MovieListFragment.newInstance("top_rated")
            2 -> MovieListFragment.newInstance("upcoming")
            else -> {
                throw IllegalStateException("Illegal position")
            }
        }
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

}