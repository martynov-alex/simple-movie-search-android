package ru.martynovalex.simplemoviesearch.domain.api

import ru.martynovalex.simplemoviesearch.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }
}