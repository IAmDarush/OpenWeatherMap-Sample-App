package com.example.dariush.data.remote

import com.example.dariush.data.Result
import com.example.dariush.data.model.ApiErrorResponse
import com.example.dariush.data.model.WeatherResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
  private val weatherAPiService: WeatherApiService
) : WeatherRepository {

  override suspend fun fetchLocationData(location: String): Result<WeatherResponseModel> =
    withContext(dispatcher) {
      try {
        val response = weatherAPiService.getCurrentWeather(location)
        val body = response.body()
        if (response.isSuccessful && body != null) {
          Result.Success(body)
        } else {
          val gson = Gson()
          val type = object : TypeToken<ApiErrorResponse>() {}.type
          val errorResponse: ApiErrorResponse? =
            gson.fromJson(response.errorBody()?.charStream(), type)
          Result.Error(Exception(errorResponse?.message))
        }
      } catch (e: Exception) {
        Result.Error(e)
      }
    }

}