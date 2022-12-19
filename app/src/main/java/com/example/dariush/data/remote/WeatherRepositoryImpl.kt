package com.example.dariush.data.remote

import com.example.dariush.data.Result
import com.example.dariush.data.model.WeatherResponseModel
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
          Result.Error(Exception(response.errorBody().toString()))
        }
      } catch (e: Exception) {
        Result.Error(e)
      }
    }

}