package ru.martynovalex.simplemoviesearch.data

import ru.martynovalex.simplemoviesearch.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}