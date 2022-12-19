package com.example.dariush.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dariush.data.remote.WeatherRepository
import com.example.dariush.data.succeeded
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
    val isLoading: Boolean = false
  )

  sealed class Event {}

  override val _uiState = MutableLiveData(UiState(isLoading = true))

  init {

    viewModelScope.launch {
      val result = weatherRepository.fetchLocationData("Istanbul")
      when (result.succeeded) {
        true  -> {
          TODO("unimplemented")
        }
        false -> {
          TODO("unimplemented")
        }
      }
    }

    viewModelScope.launch {
      eventsFlow.collect { event ->
        when (event) {
          else -> {}
        }
      }
    }
  }

}
