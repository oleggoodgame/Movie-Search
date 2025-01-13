package com.example.moviersearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET(".")
    fun getsData(
        @Query("s") sign: String,
        @Query("page") page: String = "1",
        @Query("type") type: String?,
        @Query("y") year: String?,
        @Query("apikey") apiKey: String = "89110383"
    ): Call<MovieResponse>
    @GET(".")
    fun getsDataT(
        @Query("t") sign: String,
        @Query("y") year: String,
        @Query("i") posterID: String,
        @Query("type") type:String,

        @Query("apikey") apiKey: String = "89110383"
    ): Call<MovieResponseT>
    @GET(".")
    fun getsDataImage(
        @Query("i") posterID: String,
        @Query("apikey") apiKey: String = "89110383"
    ): Call<MovieResponseT>
}