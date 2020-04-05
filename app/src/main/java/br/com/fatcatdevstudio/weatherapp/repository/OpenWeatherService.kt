package br.com.fatcatdevstudio.weatherapp.repository

import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

  @GET("data/2.5/weather?")
  fun getCurrentWeatherByCity(
    @Query("q") city: String,
    @Query("APPID") app_id: String,
    @Query("units") units: String = "metric",
    @Query("lang") lang: String = "pt_br"
  ): Call<OpenWeatherResponse>

}
