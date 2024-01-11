package com.coolrinet.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolrinet.movies.adapter.MovieSearchListAdapter
import com.coolrinet.movies.databinding.FragmentMovieSearchListBinding
import com.coolrinet.movies.viewmodel.MovieSearchListViewModel
import com.coolrinet.movies.viewmodel.MovieSearchListViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSearchListFragment : Fragment() {
    private var _binding: FragmentMovieSearchListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: MovieSearchListFragmentArgs by navArgs()

    private val viewModel: MovieSearchListViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<MovieSearchListViewModelFactory> { factory ->
                factory.create(args.searchQuery)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieSearchListBinding.inflate(inflater, container, false)

        binding.movieSearchListRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieSearchList.collect {
                    binding.movieSearchListRecyclerView.adapter =
                        MovieSearchListAdapter(it) { movie ->
                            findNavController().navigate(
                                MovieSearchListFragmentDirections.addSelectedMovieToWatch(movie.title)
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}