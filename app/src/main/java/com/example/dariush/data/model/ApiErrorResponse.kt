package com.example.dariush.data.model

import com.google.gson.annotations.SerializedName


data class ApiErrorResponse(
  @SerializedName("cod") val code: String,
  @SerializedName("message") val message: String
)