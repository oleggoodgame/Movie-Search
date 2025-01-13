package com.example.moviersearch

data class MovieResponse(
    val Search: List<MovieData>?,
    val totalResults: String?,
    val Response: String,
    val Error: String? = null
)

data class MovieData(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

data class MovieResponseT(
    val Title: String,
    val Released: String,
    val Rated: String,
    val Genre: String,
    val Awards: String,
    val Writer: String,
    val Director: String,
    val Actors: String,
    val Language: String,
    val Country: String,
    val Plot: String,
    val Poster: String,
    val Response: String?,
    val Error: String?,
    val imdbID: String
    )