package br.com.fatcatdevstudio.weatherapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.fatcatdevstudio.weatherapp.R
import br.com.fatcatdevstudio.weatherapp.Utils.Companion.hideSoftKeyBoard
import br.com.fatcatdevstudio.weatherapp.repository.api.InitializeRetrofit
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.WeatherViewModel
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.factory.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.result_weather_search.*

class WeatherActivity : AppCompatActivity() {

  private val weatherViewModel by lazy {
    val retrofit = InitializeRetrofit()
    val factory = WeatherViewModelFactory(retrofit)
    ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btnSearch.setOnClickListener {
      weatherViewModel.getWeather(this, editTextCityInput, cityResponseTextView)
      hideSoftKeyBoard(this, editTextCityInput)
      editTextCityInput.clearFocus()
    }
  }
}
