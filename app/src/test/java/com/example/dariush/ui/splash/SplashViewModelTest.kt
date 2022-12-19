package com.example.dariush.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dariush.base.MainCoroutineRule
import com.example.dariush.data.Result
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  val taskExecutor = InstantTaskExecutorRule()

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
  fun `Given weather data is being fetched, When fetch is successful, Then navigate to the search screen`() =
    runTest {
      val defaultLocation = "Istanbul"
      val dummyResponse = WeatherResponseModel()
      mockWeatherRepository.apply {
        coEvery { fetchLocationData(defaultLocation) } coAnswers {
          delay(2000)
          Result.Success(dummyResponse)
        }
      }
      val events = mutableListOf<SplashViewModel.Event>()
      val vm = SplashViewModel(mockWeatherRepository)
      val eventsJob = launch(UnconfinedTestDispatcher(testScheduler)) {
        vm.eventsFlow.collect { event ->
          events.add(event)
        }
      }

      advanceTimeBy(2001)

      vm.uiState.value?.defaultWeatherData shouldBe dummyResponse
      events shouldContain SplashViewModel.Event.NavigateToSearchScreen
      eventsJob.cancel()
    }

  @Test
  fun `Given weather data is fetching, When fetch fails, Then show error and exit the app`() {
    TODO("unimplemented")
  }

}