package fr97.movieinfo.feature.home

import android.database.DataSetObserver
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import fr97.movieinfo.R
import fr97.movieinfo.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        const val GRID_SPAN_COUNT = 2
        const val TABS_COUNT = 3
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var tabPageAdapter: TabPageAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupTabLayout()
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupTabLayout() {
        if (tabPageAdapter == null)
            tabPageAdapter = TabPageAdapter(activity!!.supportFragmentManager, TABS_COUNT)
        binding.viewPager.offscreenPageLimit = TABS_COUNT;
        binding.viewPager.adapter = tabPageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onPause() {
        super.onPause()
        Log.d("FRAG", "Paused")
    }

}
