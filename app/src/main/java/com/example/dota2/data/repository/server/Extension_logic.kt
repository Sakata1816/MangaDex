package com.example.dota2.data.repository.server

import com.example.dota2.data.remote.mangaDex.dto.ErrorResponseDto
import com.google.gson.Gson
import retrofit2.Response
import kotlin.jvm.java



private val gson = Gson()

internal fun Response<*>.toException(): Exception {

    val errorJson = errorBody()?.string()

    return try {

        val error = gson.fromJson(
            errorJson,
            ErrorResponseDto::class.java
        )

        Exception(
            error.errors
                ?.firstOrNull()
                ?.detail
                ?: "Unknown API error"
        )

    } catch (e: Exception) {

        Exception("Unknown error")
    }
}


fun <T, R> Response<T>.toResult(
    mapper: (T) -> R
): Result<R> {

    return if (isSuccessful) {

        val body = body()

        if (body != null) {

            Result.success(
                mapper(body)
            )

        } else {
            Result.failure(
                Exception("Body is null")
            )
        }

    } else {
        Result.failure(
            toException()
        )
    }
}