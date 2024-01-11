package com.coolrinet.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolrinet.movies.R
import com.coolrinet.movies.adapter.MovieListAdapter
import com.coolrinet.movies.databinding.FragmentMovieListBinding
import com.coolrinet.movies.viewmodel.MovieListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: MovieListViewModel by viewModels()

    private var movieListAdapter: MovieListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.movieRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.app_name)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_movie_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete_selected_movies -> {
                        if (viewModel.moviesToDelete.isEmpty()) {
                            Snackbar.make(
                                view,
                                R.string.delete_movies_no_selected_items,
                                Snackbar.LENGTH_SHORT
                            ).show()
                            return false
                        }
                        viewModel.deleteMovies()
                        Snackbar.make(
                            view,
                            R.string.delete_movies_success,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            addMovieToWatchFab.setOnClickListener {
                findNavController().navigate(
                    MovieListFragmentDirections.addMovieToWatch()
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { movies ->
                    movieListAdapter = MovieListAdapter(movies) { movie ->
                        viewModel.changeMovieDeletionStatus(movie)
                    }
                    binding.movieRecyclerView.adapter = movieListAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        movieListAdapter = null
    }
}