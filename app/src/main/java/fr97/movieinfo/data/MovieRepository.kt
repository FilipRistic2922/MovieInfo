package fr97.movieinfo.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.work.impl.utils.LiveDataUtils
import fr97.movieinfo.core.di.Injector
import fr97.movieinfo.core.util.AppExecutors
import fr97.movieinfo.core.util.Network
import fr97.movieinfo.data.api.MovieApi
import fr97.movieinfo.data.api.RemoteMovieDataSourceFactory
import fr97.movieinfo.data.db.MovieDao
import fr97.movieinfo.data.entity.MovieEntity
import fr97.movieinfo.feature.moviedetails.MovieDetailsModel
import fr97.movieinfo.feature.movielist.MovieListItemModel
import io.reactivex.Observable

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi,
    executors: AppExecutors
) {

    fun getMovie(movieId: Int): LiveData<MovieDetailsModel> {

        return Transformations.map(movieApi.getMovieDetails(movieId)) {
            MovieDetailsModel()
        }
    }

    fun getMovieDataSourceFactory(sorting: String): DataSource.Factory<Int, MovieListItemModel> {

//        if (Network.hasInternet(context))
//            return RemoteMovieDataSourceFactory(sorting).map {
//                MovieListItemModel(
//                    it.id,
//                    it.title,
//                    it.thumbnailPath ?: "",
//                    emptyList()
//                )
//            }
        return RemoteMovieDataSourceFactory(sorting).map {
            MovieListItemModel(
                it.id,
                it.title,
                it.thumbnailPath ?: "",
                emptyList()
            )
        }
//        return movieDao.findAllMoviesPaged(sorting)
//            .map { MovieListItemModel(it.id, it.title, it.thumbnailPath, emptyList()) }

    }


    fun save(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }

    fun saveAll(movies: List<MovieEntity>) {
        Log.d("MOVIE_REPO", "Saving movies ${movies.size}")
        movieDao.saveMovies(movies)
    }

    companion object {

        private var instance: MovieRepository? = null

        @Synchronized
        fun getInstance(
            movieDao: MovieDao,
            movieApi: MovieApi,
            executors: AppExecutors = Injector.appExecutors
        ): MovieRepository {
            if (instance == null)
                instance = MovieRepository(movieDao, movieApi, executors)

            return instance ?: throw IllegalStateException("MovieRepository can't be null")
        }
    }
}