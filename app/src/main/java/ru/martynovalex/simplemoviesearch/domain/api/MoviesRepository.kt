package ru.martynovalex.simplemoviesearch.domain.api

import ru.martynovalex.simplemoviesearch.domain.models.Movie
import ru.martynovalex.simplemoviesearch.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}