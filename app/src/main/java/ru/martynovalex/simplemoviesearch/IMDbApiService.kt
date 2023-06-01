package ru.martynovalex.simplemoviesearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie/API_KEY/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}