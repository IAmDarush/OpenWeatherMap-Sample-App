package com.example.dariush.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dariush.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi

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

    binding.tilSearchInput.setEndIconOnClickListener {
      viewModel.search(binding.etSearchInput.text.toString())
    }
  }
}