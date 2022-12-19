package com.example.dariush.data.remote

import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.Result

interface WeatherRepository {

  suspend fun fetchLocationData(location: String): Result<WeatherResponseModel>

}