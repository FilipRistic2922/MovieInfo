package fr97.movieinfo.feature.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr97.movieinfo.R
import fr97.movieinfo.databinding.ListItemMovieBinding
import java.lang.IllegalStateException
class MoviePagedListAdapter(val onClickListener: (MovieListItemModel) -> Unit) :
    PagedListAdapter<MovieListItemModel, MoviePagedListAdapter.MoviePagedViewHolder>(DIFF_CALLBACK) {


    // private lateinit var movieItemBinding: ListItemMovieBinding

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     * can use this viewType integer to provide a different layout.
     * @return A new MoviePagedViewHolder that holds the MovieListItemBinding
     */
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MoviePagedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieItemBinding: ListItemMovieBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.list_item_movie, parent, false
        )

        return MoviePagedViewHolder(movieItemBinding)
    }

    /**
     * Called by the RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(@NonNull holder: MoviePagedViewHolder, position: Int) {
        holder.bind(getItem(position) ?: throw IllegalStateException("Movie cant be null"))
    }

    /**
     * Cache of the children views for a list item.
     */
    inner class MoviePagedViewHolder(private val movieItemBinding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(movieItemBinding.getRoot()), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(movie: MovieListItemModel) {

            val thumbnail = IMAGE_URL_BASE + IMAGE_SIZE + movie.thumbnailPath
            Glide.with(itemView.context)
                .load(thumbnail)
                .placeholder(R.drawable.ic_animated_loading_circle)
                .error(R.drawable.image_not_found)
                .into(movieItemBinding.imageViewThumbnail)

            // Display the title
            movieItemBinding.textViewTitle.text = movie.title
        }

        /**
         * Called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val movie = getItem(adapterPosition) ?: throw IllegalStateException("Movie shouldn't be null")
            onClickListener(movie)
        }
    }

    companion object {
        const val IMAGE_SIZE = "w185"
        const val IMAGE_URL_BASE = "https://image.tmdb.org/t/p/"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieListItemModel>() {
            override fun areItemsTheSame(oldItem: MovieListItemModel, newItem: MovieListItemModel): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieListItemModel, newItem: MovieListItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

