package com.example.moviersearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviersearch.databinding.FragmentMainBinding
import com.example.moviersearch.databinding.FragmentOverViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverViewFragment : Fragment() {
    private val viewModel: PageViewModel by activityViewModels()
    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!
    var fifi = true
    var requireText = ""
    var year = ""
    var poster = ""
    var type = ""
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val args = RecyclerViewFragmentArgs.fromBundle(requireArguments())
            requireText = args.Search
            year = args.Year
            poster = args.Poster
            type = args.type
            id = args.imbID
        }catch (ex: Exception){
            //...
        }
        if(year.contains("–"))
        {
            val yearParts = year.split("–")
            year = yearParts[0]
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)
        binding.imageButton.setOnClickListener{
            val navController = findNavController()
            navController.popBackStack()
        }
        val movie = MovieData(Title = requireText, Year=year, Poster=poster, Type = type, imdbID = id)
        val isFavorite = viewModel.favoriteItems.any { it.imdbID == movie.imdbID }

        if (isFavorite) {
            binding.imageButton2.setImageResource(R.drawable.favorate_fill)
            fifi = false
        }
        binding.imageButton2.setOnClickListener{
            if(fifi == true){
                binding.imageButton2.setImageResource(R.drawable.favorate_fill)
                fifi=false
                viewModel.favoriteItems.add(movie)
            }
            else{
                binding.imageButton2.setImageResource(R.drawable.favorite)
                fifi=true
                viewModel.favoriteItems.removeIf { it.imdbID == movie.imdbID }
            }
        }
        val call = RetrofitInstance.api.getsDataT(requireText,year,id, type)
        binding.progressBar2.visibility = View.VISIBLE
        try {
            call.enqueue(object : Callback<MovieResponseT> {
                override fun onResponse(
                    call: Call<MovieResponseT>,
                    response: Response<MovieResponseT>
                ) {
                    if (response.isSuccessful) {
                        val dataRespons = response.body()
                        if (dataRespons?.Response == "True") {
                            binding.titleT.text = dataRespons.Title
                            binding.yearReply.text = dataRespons.Released
                            binding.ratedReply.text = dataRespons.Rated
                            binding.genreReply.text = dataRespons.Genre
                            binding.awardsReply.text = dataRespons.Awards
                            binding.writerReply.text = dataRespons.Writer
                            binding.directorReply.text = dataRespons.Director
                            binding.actorsReply.text = dataRespons.Actors
                            binding.countryReply.text = dataRespons.Country
                            binding.languageReply.text = dataRespons.Language
                            binding.textView7.text = dataRespons.Plot
                            if (dataRespons.Poster == "N/A") {
                                binding.imageView.setImageResource(R.drawable.none)
                            } else {
                                Glide.with(context!!)
                                    .load(dataRespons.Poster)
                                    .into(binding.imageView)
                            }
                            binding.progressBar2.visibility = View.GONE
                        } else {
                            Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")
                            val call2 = RetrofitInstance.api.getsDataImage(id)
                            call2.enqueue(object : Callback<MovieResponseT> {
                                override fun onResponse(
                                    call: Call<MovieResponseT>,
                                    response: Response<MovieResponseT>
                                ) {
                                    if (response.isSuccessful) {
                                        val dataRespons = response.body()
                                        if (dataRespons?.Response == "True") {
                                            binding.titleT.text = dataRespons.Title
                                            binding.yearReply.text = dataRespons.Released
                                            binding.ratedReply.text = dataRespons.Rated
                                            binding.genreReply.text = dataRespons.Genre
                                            binding.awardsReply.text = dataRespons.Awards
                                            binding.writerReply.text = dataRespons.Writer
                                            binding.directorReply.text = dataRespons.Director
                                            binding.actorsReply.text = dataRespons.Actors
                                            binding.countryReply.text = dataRespons.Country
                                            binding.languageReply.text = dataRespons.Language
                                            binding.textView7.text = dataRespons.Plot
                                            if (dataRespons.Poster == "N/A") {
                                                binding.imageView.setImageResource(R.drawable.none)
                                            } else {
                                                Glide.with(context!!)
                                                    .load(dataRespons.Poster)
                                                    .into(binding.imageView)
                                            }
                                            binding.progressBar2.visibility = View.GONE
                                        } else {
                                            Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")

                                        }
                                    } else {
                                        Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")
                                    }
                                }

                                override fun onFailure(call: Call<MovieResponseT>, t: Throwable) {
                                    Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "$t")
                                    binding.progressBar2.visibility = View.GONE
                                }
                            })
                        }
                    } else {
                        Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")
                        val call2 = RetrofitInstance.api.getsDataImage(id)
                        call2.enqueue(object : Callback<MovieResponseT> {
                            override fun onResponse(
                                call: Call<MovieResponseT>,
                                response: Response<MovieResponseT>
                            ) {
                                if (response.isSuccessful) {
                                    val dataRespons = response.body()
                                    if (dataRespons?.Response == "True") {
                                        binding.titleT.text = dataRespons.Title
                                        binding.yearReply.text = dataRespons.Released
                                        binding.ratedReply.text = dataRespons.Rated
                                        binding.genreReply.text = dataRespons.Genre
                                        binding.awardsReply.text = dataRespons.Awards
                                        binding.writerReply.text = dataRespons.Writer
                                        binding.directorReply.text = dataRespons.Director
                                        binding.actorsReply.text = dataRespons.Actors
                                        binding.countryReply.text = dataRespons.Country
                                        binding.languageReply.text = dataRespons.Language
                                        binding.textView7.text = dataRespons.Plot
                                        if (dataRespons.Poster == "N/A") {
                                            binding.imageView.setImageResource(R.drawable.none)
                                        } else {
                                            Glide.with(context!!)
                                                .load(dataRespons.Poster)
                                                .into(binding.imageView)
                                        }
                                        binding.progressBar2.visibility = View.GONE
                                    } else {
                                        Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")

                                    }
                                } else {
                                    Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "${response.code()}")
                                }
                            }

                            override fun onFailure(call: Call<MovieResponseT>, t: Throwable) {
                                Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "$t")
                                binding.progressBar2.visibility = View.GONE
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<MovieResponseT>, t: Throwable) {
                    Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "$t")
                    binding.progressBar2.visibility = View.GONE
                }
            })
        }catch (e:Exception){
            Log.d("SUKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "$e")
            Log.d("SYSUTE", "$e")

        }



        return binding.root



    }


}