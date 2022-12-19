package com.example.dariush.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import com.example.dariush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import javax.inject.Inject

const val KEY_WEATHER_DATA = "key_weather_data"

@ObsoleteCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val weatherRepository: WeatherRepository
) : BaseViewModel<SearchViewModel.Event, SearchViewModel.UiState>() {

  data class UiState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val weatherDataModel: WeatherResponseModel? = null
  )

  sealed class Event {

  }

  override val _uiState = MutableLiveData(UiState(isLoading = true))

  init {
    savedStateHandle.get<WeatherResponseModel>(KEY_WEATHER_DATA)?.let { model ->
      _uiState.value = _uiState.value?.copy(
        isLoading = false,
        searchText = model.name.orEmpty(),
        weatherDataModel = model
      )
    }
  }

}
