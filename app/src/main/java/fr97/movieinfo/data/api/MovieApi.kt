package fr97.movieinfo.data.api

import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import fr97.movieinfo.data.entity.MovieEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{sorting}")
    fun getMovies(
        @Path("sorting") sorting: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = Client.TMDB_API_KEY,
        @Query("language") language: String = Client.LANG
    ): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = Client.TMDB_API_KEY,
        @Query("language") language: String = Client.LANG,
        @Query("append_to_response") credits: String = "credits"
    ): LiveData<MovieDetailsResponse>

    @GET("movie/{id}/reviews")
    fun getMovieReviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = Client.TMDB_API_KEY,
        @Query("language") language: String = Client.LANG,
        @Query("page") page: Int
    ): Call<MovieReviewResponse>

    @GET("movie/{id}/videos")
    fun getVideos(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = Client.TMDB_API_KEY,
        @Query("language") language: String = Client.LANG
    ): Call<MovieTrailerResponse>

}

data class MoviesResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("results")
    val movies: List<MovieResponse>
) {
}

data class MovieReviewResponse(val id: Int) {

}

data class MovieTrailerResponse(val id: Int) {

}

data class MovieDetailsResponse(val id: Int) {

}

data class MovieResponse(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val thumbnailPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genres: List<Int>
)





























