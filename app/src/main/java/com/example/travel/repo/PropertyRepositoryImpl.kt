package com.example.travel.repo

import android.content.Context
import android.util.Log
import com.example.travel.core.Resource
import com.example.travel.models.Result
import com.example.travel.models.ResultResponse
import com.example.travel.models.toResult
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PropertyRepositoryImpl(
    private val context: Context
) : PropertyRepository {
    override suspend fun getListings(): Resource<ArrayList<Result>> {
        return safeApiCall(Dispatchers.IO) {
            val jsonString =
                context.assets.open("listings.json").bufferedReader().use { it.readText() }
            val response = HouseJsonParser.parseJson(jsonString)
            val listingResponseItems = response.map { it.toResult() }
            ArrayList(listingResponseItems)
        }
    }
}

object HouseJsonParser {
    fun parseJson(jsonString: String): List<ResultResponse> {
        val gson = Gson()
        val listType = object : TypeToken<List<ResultResponse>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}

data class ErrorResponse(
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("errors")
    val errors: List<String?>? = null,
    @field:SerializedName("status")
    val status: String? = null
)

fun errorBodyAsString(throwable: HttpException): String? {
    val reader = throwable.response()?.errorBody()?.charStream()
    return reader?.use { it.readText() }
}

private fun convertStringErrorResponseToJsonObject(jsonString: String): ErrorResponse? {
    val gson = Gson()
    return gson.fromJson(jsonString, ErrorResponse::class.java)
}
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Resource.Error(
                        message = "Please check your internet connection and try again later",
                        throwable = throwable
                    )
                }
                is HttpException -> {
                    val stringErrorBody = errorBodyAsString(throwable)
                    if (stringErrorBody != null) {
                        val errorResponse = convertStringErrorResponseToJsonObject(stringErrorBody)
                        Resource.Error(
                            message = errorResponse?.message,
                            throwable = throwable
                        )
                    } else {
                        Resource.Error(
                            message = "Unknown failure occurred, please try again later",
                            throwable = throwable
                        )
                    }
                }
                else -> {
                    Log.e("safeApiCall", "Unknown failure occurred", throwable)
                    Resource.Error(
                        message = "Unknown failure occurred, please try again later",
                        throwable = throwable
                    )
                }
            }
        }
    }
}
