package com.coolrinet.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.coolrinet.movies.R
import com.coolrinet.movies.data.network.MovieSearchItem
import com.coolrinet.movies.databinding.ListItemMovieSearchBinding

class MovieSearchItemHolder(
    private val binding: ListItemMovieSearchBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movieSearchItem: MovieSearchItem,
        onMovieClicked: (MovieSearchItem) -> Unit,
    ) {
        binding.searchMovieTitle.text = movieSearchItem.title
        binding.searchMovieYear.text = movieSearchItem.year
        binding.searchMovieType.text = movieSearchItem.type

        binding.searchMoviePoster.load(movieSearchItem.poster) {
            placeholder(R.drawable.movie_poster_placeholder)
        }

        binding.root.setOnClickListener {
            onMovieClicked(movieSearchItem)
        }
    }
}

class MovieSearchListAdapter(
    private val movieSearchList: List<MovieSearchItem>,
    private val onMovieClicked: (MovieSearchItem) -> Unit,
) : RecyclerView.Adapter<MovieSearchItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieSearchBinding.inflate(inflater, parent, false)
        return MovieSearchItemHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieSearchItemHolder, position: Int) {
        val item = movieSearchList[position]
        holder.bind(item, onMovieClicked)
    }

    override fun getItemCount() = movieSearchList.size

}