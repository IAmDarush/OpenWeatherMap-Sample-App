package com.example.dariush.ui.search

data class SearchItemViewModel(
  val key: String = "",
  val value: Any? = null
) {
  val valueString: String
    get() = value.toString()
}