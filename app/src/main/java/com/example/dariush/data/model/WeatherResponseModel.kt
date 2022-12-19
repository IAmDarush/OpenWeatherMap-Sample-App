package com.example.dariush.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponseModel(
  @SerializedName("coord") val coord: Coord? = Coord(),
  @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
  @SerializedName("base") var base: String? = null,
  @SerializedName("main") var main: Main? = Main(),
  @SerializedName("visibility") var visibility: Int? = null,
  @SerializedName("wind") var wind: Wind? = Wind(),
  @SerializedName("clouds") var clouds: Clouds? = Clouds(),
  @SerializedName("dt") var dt: Int? = null,
  @SerializedName("sys") var sys: Sys? = Sys(),
  @SerializedName("timezone") var timezone: Int? = null,
  @SerializedName("id") var id: Int? = null,
  @SerializedName("name") var name: String? = null,
  @SerializedName("cod") var cod: Int? = null
) : Serializable {

  fun getFlattenedMap(): Map<String, Any?> {
    return mutableMapOf(
      "coord" to coord,
      "weather" to weather,
      "base" to base,
      "main" to main,
      "visibility" to visibility,
      "wind" to wind,
      "clouds" to clouds,
      "dt" to dt,
      "sys" to sys,
      "timezone" to timezone,
      "id" to id,
      "name" to name,
      "cod" to cod
    )
  }

}


data class Coord(
  @SerializedName("lon") var lon: Double? = null,
  @SerializedName("lat") var lat: Double? = null
) : Serializable

data class Weather(
  @SerializedName("id") var id: Int? = null,
  @SerializedName("main") var main: String? = null,
  @SerializedName("description") var description: String? = null,
  @SerializedName("icon") var icon: String? = null
) : Serializable

data class Main(
  @SerializedName("temp") var temp: Double? = null,
  @SerializedName("feels_like") var feelsLike: Double? = null,
  @SerializedName("temp_min") var tempMin: Double? = null,
  @SerializedName("temp_max") var tempMax: Double? = null,
  @SerializedName("pressure") var pressure: Int? = null,
  @SerializedName("humidity") var humidity: Int? = null
) : Serializable

data class Wind(
  @SerializedName("speed") var speed: Double? = null,
  @SerializedName("deg") var deg: Int? = null
) : Serializable

data class Clouds(
  @SerializedName("all") var all: Int? = null
) : Serializable


data class Sys(
  @SerializedName("type") var type: Int? = null,
  @SerializedName("id") var id: Int? = null,
  @SerializedName("country") var country: String? = null,
  @SerializedName("sunrise") var sunrise: Int? = null,
  @SerializedName("sunset") var sunset: Int? = null
) : Serializable