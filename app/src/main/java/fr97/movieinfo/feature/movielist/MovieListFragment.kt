package fr97.movieinfo.feature.movielist


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr97.movieinfo.R
import fr97.movieinfo.core.di.Injector
import fr97.movieinfo.core.util.Network
import fr97.movieinfo.databinding.FragmentMovieListBinding
import fr97.movieinfo.feature.home.HomeFragmentDirections

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var filter: String
    private lateinit var moviePagedListAdapter: MoviePagedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WTFF", "Created Movie List")
        binding = DataBindingUtil.inflate(inflater, fr97.movieinfo.R.layout.fragment_movie_list, container, false)
//        setupRecyclerView()
//        setupSwipeRefresh()
        setupRecyclerView()
        setupSwipeRefresh()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filter = arguments?.getString("filter") ?: ""
        Log.d("WTFF", "Yo $filter" )
        viewModel = ViewModelProviders
            .of(this, Injector.movieListViewModelFactory(this.requireContext(), filter))
            .get(MovieListViewModel::class.java)
        refreshAdapter()
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.recyclerViewMovies.layoutManager = layoutManager
        binding.recyclerViewMovies.setHasFixedSize(true)
        moviePagedListAdapter = MoviePagedListAdapter {
            val action = HomeFragmentDirections.actionListToMovieDetails(it.id)
            binding.root.findNavController().navigate(action)
            Toast.makeText(this@MovieListFragment.requireContext(), "Item $it", Toast.LENGTH_LONG).show()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        Log.d("WTFF", "Start $filter")
//        refreshAdapter()
//    }

    private fun setupSwipeRefresh() {
        // Set the colors used in the progress animation
        binding.swipeRefresh.setColorSchemeColors(
            resources.getColor(
                R.color.secondaryColor,
                resources.newTheme()
            )
        )
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            when (Network.hasInternet(requireContext())) {
                true -> notifyDataUpdated()
                false -> Network.showNoInternetErrorToast(requireContext())
            }
        }
    }

    private fun refreshAdapter() {
//        viewModel.reload()
        binding.recyclerViewMovies.adapter = moviePagedListAdapter
        viewModel.getPagedMovieList()
            .observe(this,
                Observer<PagedList<MovieListItemModel>> { movies ->
                    binding.recyclerViewMovies.visibility = View.VISIBLE
                    if (movies != null) {
                        moviePagedListAdapter.submitList(movies)
                    }
                })
        Log.d("LIST", "HUHU")
    }

    private fun notifyDataUpdated() {
        Toast.makeText(requireContext(), "Updated Movies", Toast.LENGTH_SHORT).show()
    }


    companion object {
        @JvmStatic
        fun newInstance(filter: String): MovieListFragment {
            val fragment = MovieListFragment()
            val args = Bundle()
            Log.d("WTFF", "Filter is $filter")
            args.putString("filter", filter)
            fragment.arguments = args
            return fragment
        }
    }
}
