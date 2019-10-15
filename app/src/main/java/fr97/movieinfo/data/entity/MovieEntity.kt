package fr97.movieinfo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import fr97.movieinfo.data.api.MovieDetailResponse
import java.time.LocalDate

//@Entity(tableName = "movies", indices = [Index(value = arrayOf("movie_id"), name = "unique_movie")])
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    @ColumnInfo(name = "poster_path")
    val thumbnailPath: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val sorting: String = "",
    val genres: String = ""
//        @ColumnInfo(name = "movie_id")
//    val movieId: Int = 0,
//    val releaseDate: String = ""
//    val overview: String,
//    @ColumnInfo(name = "vote_average")
//    val voteAverage: Double = 0.0,
//    @ColumnInfo(name = "release_date")
//    val releaseDate: String = "",
//    @ColumnInfo(name = "backdrop_path")
//    val backdropPath: String = "",
//    val runtime: String,
//    @ColumnInfo(name = "release_year")
//    val releaseYear: String = "",
//    val sorting: String = "",
//    val genre: String = "",
//    val dateAdded: LocalDate = LocalDate.now()
)