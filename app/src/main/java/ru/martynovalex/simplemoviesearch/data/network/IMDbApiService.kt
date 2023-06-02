package ru.martynovalex.simplemoviesearch.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.martynovalex.simplemoviesearch.data.dto.MoviesSearchResponse

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_1gdzwf8s/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}