package com.example.dariush.ui.splash

import com.example.dariush.base.MainCoroutineRule
import com.example.dariush.data.remote.WeatherRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @MockK(relaxed = true)
  lateinit var mockWeatherRepository: WeatherRepository

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
  }

  @Test
  fun `Given splash screen is opened, Then fetch weather data for the default location`() {
    val vm = SplashViewModel(mockWeatherRepository)
    val defaultLocation = "Istanbul"

    coVerify {
      mockWeatherRepository.fetchLocationData(defaultLocation)
    }
  }

  @Test
  fun `Given weather data is being fetched, When fetch is successful, Then navigate to the search screen`() {
    TODO("unimplemented")
  }

  @Test
  fun `Given weather data is fetching, When fetch fails, Then show error and exit the app`() {
    TODO("unimplemented")
  }

}