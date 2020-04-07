package br.com.fatcatdevstudio.weatherapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fatcatdevstudio.weatherapp.Constants
import br.com.fatcatdevstudio.weatherapp.R
import br.com.fatcatdevstudio.weatherapp.model.OpenWeatherResponse
import br.com.fatcatdevstudio.weatherapp.repository.api.InitializeRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository(
  private val retrofit: InitializeRetrofit = InitializeRetrofit()
) {
  private val liveData = MutableLiveData<OpenWeatherResponse>()

  fun getWeather(context: Context, citySearch: EditText): LiveData<OpenWeatherResponse> {

    val cityConverted = citySearch.text.toString()

    val apiService = retrofit.apiService()
    val call = apiService.getCurrentWeatherByCity(cityConverted, Constants.API_KEY)

    call.enqueue(object : Callback<OpenWeatherResponse> {
      @SuppressLint("DefaultLocale")
      override fun onResponse(
        call: Call<OpenWeatherResponse>?,
        response: Response<OpenWeatherResponse>?
      ) {
        if (response?.code() == 200) {
          response.body()?.let { bodyApi ->
            liveData.value = bodyApi
          }
        } else {
          Toast.makeText(
            context,
            context.getString(R.string.message_error_search),
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<OpenWeatherResponse>?, t: Throwable?) {
        Toast.makeText(context, t?.message, Toast.LENGTH_LONG).show()
      }

    })
    return liveData
  }

}
