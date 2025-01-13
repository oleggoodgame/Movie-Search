package com.example.moviersearch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: MovieAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }
}