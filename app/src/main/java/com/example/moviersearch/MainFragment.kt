package com.example.moviersearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.moviersearch.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PageViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.setCurrentPage(1)
        binding.button.setOnClickListener{
            val inputText = binding.editTextText.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(context, "Please enter text", Toast.LENGTH_SHORT).show()
            }
            else{
                val action = MainFragmentDirections.actionMainFragmentToRecyclerViewFragment(inputText, "", "", "", "")
                Navigation.findNavController(requireView()).navigate(action)
            }
        }




        return binding.root



    }
}