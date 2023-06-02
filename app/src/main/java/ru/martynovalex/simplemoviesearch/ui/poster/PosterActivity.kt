package ru.martynovalex.simplemoviesearch.ui.poster

import android.app.Activity
import android.os.Bundle
import ru.martynovalex.simplemoviesearch.util.Creator
import ru.martynovalex.simplemoviesearch.R

class PosterActivity : Activity() {

    private val posterController = Creator.providePosterController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}