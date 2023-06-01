package ru.martynovalex.simplemoviesearch.domain.impl

import ru.martynovalex.simplemoviesearch.domain.api.MoviesInteractor
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            consumer.consume(repository.searchMovies(expression))
        }
    }
}

// Можно так:

//class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {
//
//    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
//        val t = Thread {
//            consumer.consume(repository.searchMovies(expression))
//        }
//        t.start()
//    }
//}