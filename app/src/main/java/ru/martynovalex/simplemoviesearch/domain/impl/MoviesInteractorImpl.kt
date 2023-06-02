package ru.martynovalex.simplemoviesearch.domain.impl

import ru.martynovalex.simplemoviesearch.domain.api.MoviesInteractor
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import ru.martynovalex.simplemoviesearch.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when(val resource = repository.searchMovies(expression)) {
                is Resource.Success -> { consumer.consume(resource.data, null) }
                is Resource.Error -> { consumer.consume(null, resource.message) }
            }
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