package com.example.travel.core

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String? = null, throwable: Throwable? = null, data: T? = null) :
        Resource<T>(data, message, throwable)
}

fun toTitleCase(input: String): String {
    val words = input.split(" ")
    val titleCaseWords = words.map { word ->
        word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()
    }
    return titleCaseWords.joinToString(" ")
}