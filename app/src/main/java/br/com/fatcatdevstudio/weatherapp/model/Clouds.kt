package br.com.fatcatdevstudio.weatherapp.model


import com.google.gson.annotations.SerializedName

data class Clouds(
  @SerializedName("all")
  val all: Int
)
