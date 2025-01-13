package com.example.moviersearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviersearch.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private val viewModel: PageViewModel by activityViewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        recyclerViewAdapter = RecyclerViewAdapter { movie ->
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToOverViewFragment(
                    movie.Title,
                    movie.Year,
                    movie.imdbID,
                    movie.Type,
                    movie.imdbID
                )
            findNavController().navigate(action)
        }
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        recyclerViewAdapter.addItems(viewModel.favoriteItems)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButtonBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}



// проблема що то має перейти на той фрагмент просто запустити