package fr97.movieinfo.feature.moviedetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import fr97.movieinfo.R
import fr97.movieinfo.core.di.Injector
import fr97.movieinfo.feature.movielist.MovieListFragment

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, Injector.movieDetailsViewModelFactory(this.requireContext(), args.movieId))
            .get(MovieDetailsViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            val args = Bundle()
            args.putInt("movieId", movieId)
            fragment.arguments = args
            return fragment
        }
    }
}
