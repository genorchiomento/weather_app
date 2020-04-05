package br.com.fatcatdevstudio.weatherapp.repository.api.service

import br.com.fatcatdevstudio.weatherapp.Constants.Companion.API_KEY
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.API_LANGUAGE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.API_UNITS
import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

  @GET("data/2.5/weather?")
  fun getCurrentWeatherByCity(
    @Query("q") city: String,
    @Query("APPID") app_id: String = API_KEY,
    @Query("units") units: String = API_UNITS,
    @Query("lang") lang: String = API_LANGUAGE
  ): Call<OpenWeatherResponse>
}
