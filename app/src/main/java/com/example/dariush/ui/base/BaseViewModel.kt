package com.example.dariush.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@ObsoleteCoroutinesApi
abstract class BaseViewModel<Event, State> : ViewModel() {

  private val eventChannel = BroadcastChannel<Event>(Channel.BUFFERED)
  val eventsFlow = eventChannel.asFlow()

  protected abstract val _uiState: MutableLiveData<State>
  val uiState: LiveData<State> by lazy { _uiState }

  protected fun sendEvent(event: Event) {
    Timber.d("${javaClass.simpleName}[Event] -> $event")
    viewModelScope.launch {
      eventChannel.send(event)
    }
  }

}