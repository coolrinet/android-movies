package com.coolrinet.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.coolrinet.movies.R
import com.coolrinet.movies.data.database.Movie
import com.coolrinet.movies.databinding.ListItemMovieBinding

class MovieHolder(
    private val binding: ListItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieYear.text = movie.year

        if (movie.posterUrl != null) {
            binding.moviePoster.load(movie.posterUrl) {
                placeholder(R.drawable.movie_poster_placeholder)
            }
        }

        binding.root.setOnCheckedChangeListener { _, isChecked ->
            binding.movieIsSelectedCheckbox.isChecked = isChecked
        }
    }
}

class MovieListAdapter(
    private val movies: List<Movie>,
) : RecyclerView.Adapter<MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater, parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    override fun getItemCount() = movies.size
}