package ru.martynovalex.simplemoviesearch.presentation

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.martynovalex.simplemoviesearch.R

class PosterController(private val activity: Activity) {

    private lateinit var poster: ImageView

    fun onCreate() {
        poster = activity.findViewById(R.id.poster)
        val url = activity.intent.extras?.getString("poster", "")

        Glide.with(activity.applicationContext)
            .load(url)
            .into(poster)
    }
}