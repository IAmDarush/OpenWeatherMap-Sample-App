package com.example.dariush.data.remote

import com.example.dariush.BuildConfig
import com.example.dariush.data.model.WeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

  @GET("data/2.5/weather?units=metric&appid=${BuildConfig.OPEN_WEATHER_MAP_API_KEY}")
  suspend fun getCurrentWeather(@Query("q") location: String): Response<WeatherResponseModel>

}