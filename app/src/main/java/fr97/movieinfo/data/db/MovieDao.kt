package fr97.movieinfo.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import fr97.movieinfo.data.entity.MovieEntity


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun findAllMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE sorting= :sorting")
    fun findAllMoviesPaged(sorting: String) : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun findMovieByMovieId(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movieEntry: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movieList: List<MovieEntity>)

    @Delete
    fun deleteMovie(movieEntry: MovieEntity)

}