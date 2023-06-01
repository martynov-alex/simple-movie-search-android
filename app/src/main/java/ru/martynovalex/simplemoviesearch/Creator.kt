package ru.martynovalex.simplemoviesearch

import ru.martynovalex.simplemoviesearch.data.MoviesRepositoryImpl
import ru.martynovalex.simplemoviesearch.data.network.RetrofitNetworkClient
import ru.martynovalex.simplemoviesearch.domain.api.MoviesInteractor
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import ru.martynovalex.simplemoviesearch.domain.impl.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}