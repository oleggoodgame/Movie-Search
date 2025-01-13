package com.example.moviersearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviersearch.databinding.FragmentRecyclerViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewFragment : Fragment() {
    private val viewModel: PageViewModel by activityViewModels()
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding get() = _binding!!
    var requireText = ""
    var requireType: String? = null
    var year: Int? = null
    val listing = mutableListOf<MovieData>()
    var total: Int? = 0
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerViewAdapter2: RecyclerViewAdapterPage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val args = RecyclerViewFragmentArgs.fromBundle(requireArguments())
            requireText = args.Search
        }catch (ex: Exception){
            //...
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.currentPage.observe(viewLifecycleOwner) { page ->
            updatePageSelection(page)
        }
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        recyclerViewAdapter = RecyclerViewAdapter { movie ->
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToOverViewFragment(movie.Title, movie.Year, movie.Poster, movie.Type, movie.imdbID)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        if(!listing.isEmpty()){
            recyclerViewAdapter.clearItems()
            listing.clear()
        }
        if(viewModel.selectedRequitre.value == "normal"){
            requireType = null
        }
        else if(viewModel.selectedRequitre.value == "movie"){
            requireType ="movie"
        }
        else if(viewModel.selectedRequitre.value == "series"){
            requireType ="series"

        }
        else if(viewModel.selectedRequitre.value == "episode"){
            requireType ="episode"

        }
        else if(viewModel.selectedRequitre.value == "game"){
            requireType ="game"

        }
        else{
            requireType = null
        }
        year = if(viewModel.year.value==0) null else viewModel.year.value
        //..................
        val typeValue: String? = if (requireType != null) requireType else null
        val call = RetrofitInstance.api.getsData(requireText, viewModel.currentPage.value.toString(), typeValue, year.toString())
        binding.progressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                binding.button2.visibility = View.VISIBLE
                if (response.isSuccessful) {
                    val dataRespons = response.body()
                    if (dataRespons?.Search != null) {
                        total = dataRespons.totalResults!!.toInt()
                        val totalPages = Math.ceil((total ?: 0) / 10.0).toInt() // Округлення в більшу сторону
                        val pagesList = (1..totalPages).map { it.toString() } // Створення списку сторінок як рядків
                        recyclerViewAdapter2.addItems(pagesList)
                        listing.addAll(dataRespons.Search)
                        recyclerViewAdapter.addItems(listing)
                    } else {
                        binding.textView4.text = "${response.body()?.Error}"
                        binding.textView4.visibility = View.VISIBLE
                    }
                } else {
                    println("Помилка відповіді: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                println("Помилка під час виконання запиту: ${t.message}")
            }
        })
        // тут треба щоб total/10 але в більшу частину тобто якщо 2.6 то треба 3
        // і треба щоб всі цифри від 1 до того числа включно
        // далі я сам буду пробувати
//        val liststs = mutableListOf<String>.for
//        recyclerViewAdapter2 = RecyclerViewAdapterPage { page ->
//
//        }
        recyclerViewAdapter2 = RecyclerViewAdapterPage { page ->
            val call2s = RetrofitInstance.api.getsData(requireText, page, typeValue, year.toString())
            binding.progressBar.visibility = View.VISIBLE
            recyclerViewAdapter.clearItems()
            listing.clear()
            call2s.enqueue(object : Callback<MovieResponse> {

                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                    if (response.isSuccessful) {
                        val dataRespons = response.body()
                        if (dataRespons?.Search != null) {
                            total = dataRespons.totalResults!!.toInt()
                            val totalPages = Math.ceil((total ?: 0) / 10.0).toInt()
                            val pagesList = (1..totalPages).map { it.toString() }
                            recyclerViewAdapter2.addItems(pagesList)
                            listing.addAll(dataRespons.Search)
                            recyclerViewAdapter.addItems(listing)
                        } else {
                            binding.textView4.text = "${response.body()?.Error}"
                            binding.textView4.visibility = View.VISIBLE
                        }
                    } else {
                        println("Помилка відповіді: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                    println("Помилка під час виконання запиту: ${t.message}")
                }
            })
            viewModel.setCurrentPage(page.toInt())
        }
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerViewAdapter2
        }
        viewModel.selectedRequitre.observe(viewLifecycleOwner) { query ->
            viewModel.setCurrentPage(1)
            query?.let { performSearch(requireText, 1, year) }
        }
        viewModel.year.observe(viewLifecycleOwner) { year ->
            year?.let { performSearch(requireText, viewModel.currentPage.value ?: 1, year) }
        }
        binding.closeButtons.setOnClickListener{
            val navController = findNavController()
            navController.popBackStack()
        }
        binding.button2.setOnClickListener{
            recyclerViewAdapter2.moveToNextPage()
            val page = viewModel.currentPage.value?.plus(1)
            val call2s = RetrofitInstance.api.getsData(requireText, page.toString(), typeValue, year.toString())
            binding.progressBar.visibility = View.VISIBLE
            recyclerViewAdapter.clearItems()
            listing.clear()
            call2s.enqueue(object : Callback<MovieResponse> {

                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                    if (response.isSuccessful) {
                        val dataRespons = response.body()
                        if (dataRespons?.Search != null) {
                            total = dataRespons.totalResults!!.toInt()
                            val totalPages = Math.ceil((total ?: 0) / 10.0).toInt()
                            val pagesList = (1..totalPages).map { it.toString() }
                            recyclerViewAdapter2.addItems(pagesList)
                            listing.addAll(dataRespons.Search)
                            recyclerViewAdapter.addItems(listing)
                        } else {
                            binding.textView4.text = "${response.body()?.Error}"
                            binding.textView4.visibility = View.VISIBLE
                        }
                    } else {
                        println("Помилка відповіді: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE // Приховуємо індикатор
                    println("Помилка під час виконання запиту: ${t.message}")
                }
            })
            viewModel.setCurrentPage(page!!.toInt())
            binding.recyclerView2.smoothScrollToPosition(page)
        }
//        addTestData()

        return binding.root
    }

    private fun addTestData() {
        val testMovies = listOf(
            MovieData("Movie 1", "2021", "Action", "Drama", "https://m.media-amazon.com/images/M/MV5BZmRmNDI4ZDMtZTMyNy00MDM5LWE0NjAtMGZkZTFmYzUzZGY5XkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_SX300.jpg"),
            MovieData("Movie 2", "2022", "Drama", "Drama", "https://m.media-amazon.com/images/M/MV5BZmRmNDI4ZDMtZTMyNy00MDM5LWE0NjAtMGZkZTFmYzUzZGY5XkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_SX300.jpg"),
            MovieData("Movie 3", "2023", "Comedy","Drama", "https://m.media-amazon.com/images/M/MV5BZmRmNDI4ZDMtZTMyNy00MDM5LWE0NjAtMGZkZTFmYzUzZGY5XkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_SX300.jpg")
        )
        recyclerViewAdapter.addItems(testMovies)
    }
    private fun updatePageSelection(selectedPage: Int) {
        recyclerViewAdapter2.setSelectedPage(selectedPage)
    }
    private fun performSearch(query: String, page:  Int, year: Int?) {
        val Year = if (viewModel.year.value == 0) null else viewModel.year.value

        val typeValue: String? = when (viewModel.selectedRequitre.value) {
            "normal" -> null
            "movie" -> "movie"
            "series" -> "series"
            "episode" -> "episode"
            "game" -> "game"
            else -> null
        }

        val call = RetrofitInstance.api.getsData(query, page.toString(), typeValue, Year.toString())
        binding.progressBar.visibility = View.VISIBLE
        recyclerViewAdapter.clearItems()
        listing.clear()

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse?.Search != null) {
                        listing.addAll(dataResponse.Search)
                        recyclerViewAdapter2.clearItems()
                        total = dataResponse.totalResults!!.toInt()
                        val totalPages = Math.ceil((total ?: 0) / 10.0).toInt() // Округлення в більшу сторону
                        val pagesList = (1..totalPages).map { it.toString() } // Створення списку сторінок як рядків
                        recyclerViewAdapter2.addItems(pagesList)
                        recyclerViewAdapter.addItems(listing)
                    } else {
                        binding.textView4.text = "${response.body()?.Error}"
                        binding.textView4.visibility = View.VISIBLE
                    }
                } else {
                    println("Помилка відповіді: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                println("Помилка під час виконання запиту: ${t.message}")
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

