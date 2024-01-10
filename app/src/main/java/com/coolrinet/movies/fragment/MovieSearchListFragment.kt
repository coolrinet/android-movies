package com.coolrinet.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolrinet.movies.databinding.FragmentMovieSearchListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieSearchListFragment : Fragment() {
    private var _binding: FragmentMovieSearchListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieSearchListBinding.inflate(inflater, container, false)

        binding.movieSearchListRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}