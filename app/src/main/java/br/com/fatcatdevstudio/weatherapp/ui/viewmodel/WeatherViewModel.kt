package br.com.fatcatdevstudio.weatherapp.ui.viewmodel

import android.content.Context
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import br.com.fatcatdevstudio.weatherapp.repository.ApiRepository


class WeatherViewModel(
  private val repository: ApiRepository
) : ViewModel() {

  fun getWeather(
    context: Context,
    editTextCitySearch: EditText
  ): LiveData<OpenWeatherResponse> {
    return repository.getWeather(context, editTextCitySearch)
  }
}
