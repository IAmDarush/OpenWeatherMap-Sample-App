package com.example.dariush.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.dariush.base.MainCoroutineRule
import com.example.dariush.data.Result
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@OptIn(ObsoleteCoroutinesApi::class)
class SearchViewModelTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  @get:Rule
  val taskExecutor = InstantTaskExecutorRule()

  @MockK(relaxed = true)
  lateinit var mockWeatherRepository: WeatherRepository

  private val mockSavedStateHandle = SavedStateHandle()

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
  }

  @Test
  fun `Given the search screen is opened, When preloaded data is available, Then show the data`() {
    val dummyWeatherData = WeatherResponseModel(name = "Istanbul")
    val keyValueList = dummyWeatherData.getFlattenedList()
    mockSavedStateHandle[KEY_WEATHER_DATA] = dummyWeatherData
    val vm = SearchViewModel(mockSavedStateHandle, mockWeatherRepository)

    vm.uiState.value?.searchText shouldBe dummyWeatherData.name
    vm.uiState.value?.weatherDataModel shouldBe dummyWeatherData
    vm.uiState.value?.isLoading shouldBe false
    vm.uiState.value?.keyValueList?.shouldContainAll(keyValueList)
  }

  @Test
  fun `Given the user wants to perform a search, Then query the searched weather data`() {
    val vm = SearchViewModel(mockSavedStateHandle, mockWeatherRepository)

    val dummySearchQuery = "New York"
    vm.search(dummySearchQuery)

    vm.uiState.value?.isLoading shouldBe true
    vm.uiState.value?.searchText shouldBe dummySearchQuery
    vm.uiState.value?.weatherDataModel shouldBe null
    coVerify {
      mockWeatherRepository.fetchLocationData(dummySearchQuery)
    }
  }

  @Test
  fun `Given the user has performed the search, When the weather data is returned is Successful, Then show the weather data`() =
    runTest {
      val dummySearchQuery = "New York"
      val dummyWeatherData = WeatherResponseModel(name = dummySearchQuery)
      mockWeatherRepository.apply {
        coEvery { fetchLocationData(dummySearchQuery) } coAnswers {
          delay(2000)
          Result.Success(dummyWeatherData)
        }
      }
      val vm = SearchViewModel(mockSavedStateHandle, mockWeatherRepository)
      vm.search(dummySearchQuery)
      vm.uiState.value?.isLoading shouldBe true
      vm.uiState.value?.searchText shouldBe dummySearchQuery
      vm.uiState.value?.weatherDataModel shouldBe null

      advanceTimeBy(2001)

      vm.uiState.value?.isLoading shouldBe false
      vm.uiState.value?.searchText shouldBe dummySearchQuery
      vm.uiState.value?.weatherDataModel shouldBe dummyWeatherData
      coVerify {
        mockWeatherRepository.fetchLocationData(dummySearchQuery)
      }
    }

  @Test
  fun `Given the user has performed the search, When the weather data is return fails, Then show the error message`() =
    runTest {
      TODO("not implemented")
    }

}