package com.example.dariush.ui.splash

import com.example.dariush.data.remote.WeatherRepository
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Test

class SplashViewModelTest {

  @MockK(relaxed = true)
  lateinit var mockWeatherRepository: WeatherRepository

  @Test
  fun `Given splash screen is opened, Then fetch weather data for the default location`() {
    val vm = SplashViewModel()
    val defaultLocation = "Istanbul"

    coVerify {
      mockWeatherRepository.fetchLocationData(defaultLocation)
    }
  }

  @Test
  fun `Given weather data is being fetched, When fetch is successful, Then navigate to the search screen`() {

  }

  @Test
  fun `Given weather data is fetching, When fetch fails, Then show error and exit the app`() {

  }

}