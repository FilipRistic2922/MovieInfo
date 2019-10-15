package fr97.movieinfo.feature.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider
import fr97.movieinfo.data.MovieRepository
import fr97.movieinfo.feature.movielist.MovieListViewModel

class MovieDetailsViewModel(repository: MovieRepository, private val movieId: Int) : ViewModel() {
    // TODO: Implement the ViewModel


    private val movieLiveData : MutableLiveData<MovieDetailsModel>

    init {

        movieLiveData = repository.getMovie(movieId)

    }



}

class MovieDetailsViewModelFactory(private val repository: MovieRepository, private val movieId: Int) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(repository, movieId) as T
    }


}