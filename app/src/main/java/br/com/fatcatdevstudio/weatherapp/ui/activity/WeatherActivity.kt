package br.com.fatcatdevstudio.weatherapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fatcatdevstudio.weatherapp.R
import br.com.fatcatdevstudio.weatherapp.Utils.Companion.hideSoftKeyBoard
import br.com.fatcatdevstudio.weatherapp.repository.ApiRepository
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.WeatherViewModel
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.factory.WeatherViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.result_weather_search.*

class WeatherActivity : AppCompatActivity() {

  private val weatherViewModel by lazy {
    val repository = ApiRepository()
    val factory = WeatherViewModelFactory(repository)
    ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btnSearch.setOnClickListener {
      observeLiveData()
      hideSoftKeyBoard(this, editTextCityInput)
      editTextCityInput.clearFocus()
    }
  }

  private fun observeLiveData() {
    weatherViewModel.getWeather(this, editTextCityInput)
      .observe(
        this, Observer { weatherApiResponse ->
          val cityWeather = weatherApiResponse.name
          val iconWeather = weatherApiResponse.weather[0].icon
          val weatherDescription = weatherApiResponse.weather[0].description.capitalize()
          val temperature = weatherApiResponse.main.temp
          val humidity = weatherApiResponse.main.humidity
          val feelsLike = weatherApiResponse.main.feelsLike
          val windSpeed = weatherApiResponse.wind.speed

          val iconUrl = "https://openweathermap.org/img/wn/$iconWeather@2x.png"

          cityResponseTextView.text = cityWeather

          Picasso.get()
            .load(iconUrl)
            .into(iconWeatherImageView)

          weatherResponseTextView.text = weatherDescription
          humidityResponseTextView.text = getString(R.string.text_humidity, humidity)
          tempResponseTextView.text = getString(R.string.text_temperature, temperature)
          feelsLikeResponseTextView.text = getString(R.string.text_feels_like, feelsLike)
          windSpeedResponseTextView.text = getString(R.string.text_speed_wind, windSpeed)

          editTextCityInput.text.clear()
        }
      )
  }
}
