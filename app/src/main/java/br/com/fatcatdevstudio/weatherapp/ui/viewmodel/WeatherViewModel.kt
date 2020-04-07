package br.com.fatcatdevstudio.weatherapp.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

  private val liveData = MutableLiveData<OpenWeatherResponse>()

  fun getWeather(
    context: Context,
    getInputText: EditText
  ): LiveData<OpenWeatherResponse> {

    val inputCityToString = getInputText.text.toString()
    val service = retrofit.apiService()
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
            liveData.value = weatherApi
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

    return liveData
  }
}
