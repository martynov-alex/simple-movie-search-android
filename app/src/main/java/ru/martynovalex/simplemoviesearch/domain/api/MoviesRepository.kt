package ru.martynovalex.simplemoviesearch.domain.api

import ru.martynovalex.simplemoviesearch.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}