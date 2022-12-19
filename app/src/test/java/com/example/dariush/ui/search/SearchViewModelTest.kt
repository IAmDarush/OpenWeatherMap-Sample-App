package com.example.dariush.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.dariush.base.MainCoroutineRule
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
    mockSavedStateHandle[KEY_WEATHER_DATA] = dummyWeatherData
    val vm = SearchViewModel(mockSavedStateHandle, mockWeatherRepository)

    vm.uiState.value?.searchText shouldBe dummyWeatherData.name
    vm.uiState.value?.weatherDataModel shouldBe dummyWeatherData
    vm.uiState.value?.isLoading shouldBe false
  }

}