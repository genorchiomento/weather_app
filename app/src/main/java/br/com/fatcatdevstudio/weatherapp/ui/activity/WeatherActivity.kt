package br.com.fatcatdevstudio.weatherapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.API_KEY
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.CITY_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.FEELS_LIKE_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.HUMIDITY_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.ICON_URL_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.ICON_WEATHER_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.TEMP_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.WEATHER_DESCRIPTION_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.Constants.Companion.WIND_SPEED_SAVE_INSTANCE
import br.com.fatcatdevstudio.weatherapp.R
import br.com.fatcatdevstudio.weatherapp.Utils.Companion.hideSoftKeyBoard
import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import br.com.fatcatdevstudio.weatherapp.repository.api.InitializeRetrofit
import br.com.fatcatdevstudio.weatherapp.ui.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.result_weather_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

  var cityWeather: String? = null
  var iconWeather: String? = null
  var iconUrl: String? = null
  var weatherDescription: String? = null
  var temperature: Double? = null
  var humidity: Int? = null
  var feelsLike: Double? = null
  var windSpeed: Double? = null

  private val weatherViewModel by lazy {
    ViewModelProvider(this).get(WeatherViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    isSavedInstanceNull(savedInstanceState)

    btnSearch.setOnClickListener {
      getWeather()
      hideSoftKeyBoard(this, editTextCityInput)
      editTextCityInput.clearFocus()
    }
  }

  private fun isSavedInstanceNull(savedInstanceState: Bundle?) {
    if (savedInstanceState != null) {
      cityWeather = savedInstanceState.getString(CITY_SAVE_INSTANCE)
      weatherDescription = savedInstanceState.getString(WEATHER_DESCRIPTION_SAVE_INSTANCE)
      iconWeather = savedInstanceState.getString(ICON_WEATHER_SAVE_INSTANCE)
      iconUrl = savedInstanceState.getString(ICON_URL_SAVE_INSTANCE)
      temperature = savedInstanceState.getDouble(TEMP_SAVE_INSTANCE)
      humidity = savedInstanceState.getInt(HUMIDITY_SAVE_INSTANCE)
      feelsLike = savedInstanceState.getDouble(FEELS_LIKE_SAVE_INSTANCE)
      windSpeed = savedInstanceState.getDouble(WIND_SPEED_SAVE_INSTANCE)
      cityResponseTextView.text = cityWeather
      weatherResponseTextView.text = weatherDescription
      tempResponseTextView.text = getString(R.string.text_temperature, temperature)
      humidityResponseTextView.text = getString(R.string.text_humidity, humidity)
      feelsLikeResponseTextView.text = getString(R.string.text_feels_like, feelsLike)
      windSpeedResponseTextView.text = getString(R.string.text_speed_wind, windSpeed)
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    cityWeather?.let { outState.putString(CITY_SAVE_INSTANCE, it) }
    weatherDescription?.let { outState.putString(WEATHER_DESCRIPTION_SAVE_INSTANCE, it) }
    iconWeather?.let { outState.putString(ICON_WEATHER_SAVE_INSTANCE, it) }
    iconUrl?.let { outState.putString(ICON_URL_SAVE_INSTANCE, it) }
    temperature?.let { outState.putDouble(TEMP_SAVE_INSTANCE, it) }
    humidity?.let { outState.putInt(HUMIDITY_SAVE_INSTANCE, it) }
    feelsLike?.let { outState.putDouble(FEELS_LIKE_SAVE_INSTANCE, it) }
    windSpeed?.let { outState.putDouble(WIND_SPEED_SAVE_INSTANCE, it) }
  }

  private fun getWeather() {
    val inputCityToString = editTextCityInput.text.toString()
    val service = InitializeRetrofit()
      .apiService()
    val call = service.getCurrentWeatherByCity(inputCityToString, API_KEY)

    call.enqueue(object : Callback<OpenWeatherResponse> {
      override fun onFailure(call: Call<OpenWeatherResponse>?, t: Throwable?) {
        Toast.makeText(baseContext, t?.message, Toast.LENGTH_LONG).show()
      }

      @SuppressLint("DefaultLocale")
      override fun onResponse(
        call: Call<OpenWeatherResponse>?,
        response: Response<OpenWeatherResponse>?
      ) {
        if (response?.code() == 200) {
          response.body()?.let { weatherApi ->

            cityWeather = weatherApi.name
            iconWeather = weatherApi.weather[0].icon
            weatherDescription = weatherApi.weather[0].description.capitalize()
            temperature = weatherApi.main.temp
            humidity = weatherApi.main.humidity
            feelsLike = weatherApi.main.feelsLike
            windSpeed = weatherApi.wind.speed

            iconUrl = "https://openweathermap.org/img/wn/$iconWeather@2x.png"

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
        } else {
          Toast.makeText(
            baseContext,
            getString(R.string.message_error_search),
            Toast.LENGTH_LONG
          ).show()
        }
      }
    })
  }
}
