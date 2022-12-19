package com.example.dariush.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dariush.data.Result
import com.example.dariush.data.model.WeatherResponseModel
import com.example.dariush.data.remote.WeatherRepository
import com.example.dariush.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ObsoleteCoroutinesApi::class)
@HiltViewModel
class SplashViewModel @Inject constructor(
  private val weatherRepository: WeatherRepository
) : BaseViewModel<SplashViewModel.Event, SplashViewModel.UiState>() {

  data class UiState(
    val isLoading: Boolean = false,
    val defaultWeatherData: WeatherResponseModel? = null
  )

  sealed class Event {
    data class NetworkError(val errorMessage: String) : Event()
    object NavigateToSearchScreen : Event()
  }

  override val _uiState = MutableLiveData(UiState(isLoading = true))

  init {

    viewModelScope.launch {
      when (val result = weatherRepository.fetchLocationData("Istanbul")) {
        is Result.Success -> {
          _uiState.value = _uiState.value?.copy(isLoading = false, defaultWeatherData = result.data)
          sendEvent(Event.NavigateToSearchScreen)
        }
        is Result.Error   -> {
          _uiState.value = _uiState.value?.copy(isLoading = false)
          val message = result.exception.message ?: "Network error! Please try again."
          sendEvent(Event.NetworkError(message))
        }
        Result.Loading    -> {
          _uiState.value = _uiState.value?.copy(isLoading = true)
        }
      }
    }
    
  }

}
