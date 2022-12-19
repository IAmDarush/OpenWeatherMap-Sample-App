package com.example.dariush.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dariush.databinding.ActivitySplashBinding
import com.example.dariush.ui.search.KEY_WEATHER_DATA
import com.example.dariush.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySplashBinding
  private val viewModel: SplashViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySplashBinding.inflate(layoutInflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    setContentView(binding.root)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.eventsFlow.collect { event ->
          when (event) {
            is SplashViewModel.Event.NavigateToSearchScreen -> {
              navigateToSearchActivity()
            }
            is SplashViewModel.Event.NetworkError           -> {
              Toast.makeText(this@SplashActivity, event.errorMessage, Toast.LENGTH_SHORT).show()
            }
          }
        }
      }
    }

  }

  @OptIn(ObsoleteCoroutinesApi::class)
  private fun navigateToSearchActivity() {
    val bundle = Bundle().apply {
      putSerializable(KEY_WEATHER_DATA, viewModel.uiState.value?.defaultWeatherData)
    }
    val intent = Intent(this, SearchActivity::class.java).apply {
      putExtras(bundle)
    }
    startActivity(intent)
    finish()
  }

}