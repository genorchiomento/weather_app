package br.com.fatcatdevstudio.weatherapp.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fatcatdevstudio.weatherapp.repository.ApiRepository
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.WeatherViewModel

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(
  private val repository: ApiRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return WeatherViewModel(repository) as T
  }
}
