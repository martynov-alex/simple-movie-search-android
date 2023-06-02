package ru.martynovalex.simplemoviesearch.data

import ru.martynovalex.simplemoviesearch.data.dto.MoviesSearchRequest
import ru.martynovalex.simplemoviesearch.data.dto.MoviesSearchResponse
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import ru.martynovalex.simplemoviesearch.domain.models.Movie
import ru.martynovalex.simplemoviesearch.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)})
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}