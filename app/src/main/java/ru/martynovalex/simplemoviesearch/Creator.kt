package ru.martynovalex.simplemoviesearch

import android.app.Activity
import ru.martynovalex.simplemoviesearch.data.MoviesRepositoryImpl
import ru.martynovalex.simplemoviesearch.data.network.RetrofitNetworkClient
import ru.martynovalex.simplemoviesearch.domain.api.MoviesInteractor
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import ru.martynovalex.simplemoviesearch.domain.impl.MoviesInteractorImpl
import ru.martynovalex.simplemoviesearch.presentation.MoviesSearchController
import ru.martynovalex.simplemoviesearch.presentation.PosterController
import ru.martynovalex.simplemoviesearch.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}