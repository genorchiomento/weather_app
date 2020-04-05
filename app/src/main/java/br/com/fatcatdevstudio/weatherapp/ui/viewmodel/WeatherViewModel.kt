package br.com.fatcatdevstudio.weatherapp.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import br.com.fatcatdevstudio.weatherapp.Constants
import br.com.fatcatdevstudio.weatherapp.R
import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import br.com.fatcatdevstudio.weatherapp.repository.api.InitializeRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherViewModel(
  private val retrofit: InitializeRetrofit
) : ViewModel() {
  fun getWeather(
    context: Context,
    getInputText: EditText,
    cityResponseTextView: TextView
  ) {
    val inputCityToString = getInputText.text.toString()
    val service = retrofit
      .apiService()
    val call = service.getCurrentWeatherByCity(inputCityToString, Constants.API_KEY)

    call.enqueue(object : Callback<OpenWeatherResponse> {
      override fun onFailure(call: Call<OpenWeatherResponse>?, t: Throwable?) {
        Toast.makeText(context, t?.message, Toast.LENGTH_LONG).show()
      }

      @SuppressLint("DefaultLocale")
      override fun onResponse(
        call: Call<OpenWeatherResponse>?,
        response: Response<OpenWeatherResponse>?
      ) {
        if (response?.code() == 200) {
          response.body()?.let { weatherApi ->

            val cityWeather = weatherApi.name
            val iconWeather = weatherApi.weather[0].icon
            val weatherDescription = weatherApi.weather[0].description.capitalize()
            val temperature = weatherApi.main.temp
            val humidity = weatherApi.main.humidity
            val feelsLike = weatherApi.main.feelsLike
            val windSpeed = weatherApi.wind.speed

            val iconUrl = "https://openweathermap.org/img/wn/$iconWeather@2x.png"

            cityResponseTextView.text = cityWeather

//            Picasso.get()
//              .load(iconUrl)
//              .into(iconWeatherImageView)
//
//            weatherResponseTextView.text = weatherDescription
//            humidityResponseTextView.text = context.getString(R.string.text_humidity, humidity)
//            tempResponseTextView.text = context.getString(R.string.text_temperature, temperature)
//            feelsLikeResponseTextView.text = context.getString(R.string.text_feels_like, feelsLike)
//            windSpeedResponseTextView.text = context.getString(R.string.text_speed_wind, windSpeed)
//
//            editTextCityInput.text.clear()
          }
        } else {
          Toast.makeText(
            context,
            context.getString(R.string.message_error_search),
            Toast.LENGTH_LONG
          ).show()
        }
      }
    })
  }
}
