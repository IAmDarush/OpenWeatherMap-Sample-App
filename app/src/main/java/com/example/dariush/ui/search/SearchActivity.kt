package com.example.dariush.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dariush.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch

@ObsoleteCoroutinesApi
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

  lateinit var binding: ActivitySearchBinding
  private val viewModel: SearchViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySearchBinding.inflate(layoutInflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel
    setContentView(binding.root)

    init()
  }

  private fun init() {
    binding.tilSearchInput.setEndIconOnClickListener {
      viewModel.search(binding.etSearchInput.text.toString())
    }

    val adapter = SearchAdapter()
    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@SearchActivity)
      this.adapter = adapter
    }

    viewModel.uiState.observe(this) { state ->
      adapter.setItems(
        state.keyValueList.toList().map { pair -> SearchItemViewModel(pair.first, pair.second) })
    }

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.eventsFlow.collect { event ->
          when (event) {
            is SearchViewModel.Event.SearchError -> {
              Toast.makeText(this@SearchActivity, event.errorMessage, Toast.LENGTH_SHORT).show()
            }
            else                                 -> {}
          }
        }
      }
    }

  }
}