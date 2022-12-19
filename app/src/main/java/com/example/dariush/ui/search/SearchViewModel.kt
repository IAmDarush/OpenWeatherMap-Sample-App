package com.example.dariush.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.dariush.data.Result
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import com.example.dariush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
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
    val weatherDataModel: WeatherResponseModel? = null,
    val keyValueList: Map<String, Any?> = mapOf()
  )

  sealed class Event {
    data class Search(val query: String) : Event()
  }

  override val _uiState = MutableLiveData(UiState(isLoading = true))

  init {
    savedStateHandle.get<WeatherResponseModel>(KEY_WEATHER_DATA)?.let { model ->
      _uiState.value = _uiState.value?.copy(
        isLoading = false,
        searchText = model.name.orEmpty(),
        weatherDataModel = model,
        keyValueList = model.getFlattenedMap()
      )
    }

    viewModelScope.launch {
      eventsFlow.collect { event ->
        when (event) {
          is Event.Search -> {
            _uiState.value = _uiState.value?.copy(
              isLoading = true,
              searchText = event.query,
              weatherDataModel = null
            )
            viewModelScope.launch {
              when (val result = weatherRepository.fetchLocationData(event.query)) {
                is Result.Success -> {
                  _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    searchText = event.query,
                    weatherDataModel = result.data,
                    keyValueList = result.data.getFlattenedMap()
                  )
                }
                is Result.Error   -> TODO("not implemented")
                Result.Loading    -> TODO("not implemented")
              }
            }
          }
        }
      }
    }
  }

  fun search(query: String) {
    sendEvent(Event.Search(query))
  }
}
