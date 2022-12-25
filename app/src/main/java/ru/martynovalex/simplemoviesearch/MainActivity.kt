package ru.martynovalex.simplemoviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// Пример запроса: /en/API/SearchMovie/ВАШ_КЛЮЧ_API/{expression}

class MainActivity : AppCompatActivity() {
    private val imdbBaseUrl = "https://imdb-api.com"
    private val apiKey = "apiKey"

    // Инициализация Retrofit.
    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Инициализация сервиса.
    private val imdbService = retrofit.create(IMDbApi::class.java)

    private val movies = ArrayList<Movie>()
    private val adapter = MoviesAdapter()

    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeholderMessage = findViewById(R.id.placeholderMessage)
        searchButton = findViewById(R.id.searchButton)
        queryInput = findViewById(R.id.queryInput)
        moviesList = findViewById(R.id.movies)

        adapter.movies = movies

        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        searchButton.setOnClickListener {
            if (queryInput.text.isNotEmpty()) {
                searchMovies()
            }
        }
    }

    // Функция поиска.
    private fun searchMovies() {
        imdbService.getMovies(apiKey, queryInput.text.toString())
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    when (response.code()) {

                        200 -> {
                            if (response.body()?.results?.isNotEmpty() == true) {
                                movies.clear()
                                movies.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                                showMessage("", "")
                            } else {
                                showMessage(getString(R.string.nothing_found), "")
                            }
                        }
                        else -> {
                            showMessage(getString(R.string.something_went_wrong), response.code().toString())
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    showMessage(getString(R.string.something_went_wrong), t.message.toString())
                }

            })
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
}


// RecyclerView ViewHolder
class MovieViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
    ) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val artwork: ImageView = itemView.findViewById(R.id.artwork)

    fun bind(movie: Movie) {
        Glide.with(itemView.context)
            .load(movie.image)
            .centerCrop()
            .placeholder(androidx.constraintlayout.widget.R.drawable.abc_btn_borderless_material)
            .into(artwork)

        title.text = movie.title
        description.text = movie.description
    }
}

// RecyclerView Adapter
class MoviesAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}

// Интерфейс для работы с API.
interface IMDbApi {
    // Получение списка фильмов по запросу.
    @GET("/en/API/SearchMovie/{apiKey}/{expression}")
    fun getMovies(@Path("apiKey") apiKey: String, @Path("expression") expression: String):
            Call<MoviesResponse>
}

// Дата класс с информацией о фильме.
data class Movie(val title: String, val description: String, val image: String)

// Дата класс ответа сервера с прогнозом погоды для выбранного города.
data class MoviesResponse(val results: ArrayList<Movie>)