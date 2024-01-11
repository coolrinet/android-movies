package com.coolrinet.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coolrinet.movies.R
import com.coolrinet.movies.databinding.FragmentAddMovieBinding
import com.coolrinet.movies.viewmodel.AddMovieViewModel
import com.coolrinet.movies.viewmodel.AddMovieViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class AddMovieFragment : Fragment() {
    private var _binding: FragmentAddMovieBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val viewModel: AddMovieViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<AddMovieViewModelFactory> { factory ->
                factory.create()
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            movieTitleTextInputLayout.editText?.let { editText ->
                editText.setOnEditorActionListener { v, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            searchMovies(v.text.toString())
                            true
                        }

                        else -> false
                    }
                }

                editText.doOnTextChanged { text, _, _, _ ->
                    viewModel.updateMovie { oldMovie ->
                        oldMovie.copy(title = text.toString())
                    }
                }
            }

            movieReleaseYearTextInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel.updateMovie { oldMovie ->
                    oldMovie.copy(year = text.toString())
                }
            }

            addMovieButton.setOnClickListener {
                if (movieTitleTextInputLayout.editText?.text.isNullOrBlank()) {
                    Snackbar.make(
                        view,
                        R.string.add_movie_blank_title_message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.addMovie(viewModel.movie.value)
                Snackbar.make(
                    view,
                    R.string.add_movie_success_message,
                    Snackbar.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun searchMovies(searchQuery: String) {
        findNavController().navigate(
            AddMovieFragmentDirections.searchMovies(searchQuery)
        )
    }
}