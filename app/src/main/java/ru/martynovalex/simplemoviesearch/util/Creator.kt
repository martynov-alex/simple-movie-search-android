package ru.martynovalex.simplemoviesearch.util

import android.app.Activity
import android.content.Context
import ru.martynovalex.simplemoviesearch.data.MoviesRepositoryImpl
import ru.martynovalex.simplemoviesearch.data.network.RetrofitNetworkClient
import ru.martynovalex.simplemoviesearch.domain.api.MoviesInteractor
import ru.martynovalex.simplemoviesearch.domain.api.MoviesRepository
import ru.martynovalex.simplemoviesearch.domain.impl.MoviesInteractorImpl
import ru.martynovalex.simplemoviesearch.presentation.MoviesSearchController
import ru.martynovalex.simplemoviesearch.presentation.PosterController
import ru.martynovalex.simplemoviesearch.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchController(activity: Activity, adapter: MoviesAdapter): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}