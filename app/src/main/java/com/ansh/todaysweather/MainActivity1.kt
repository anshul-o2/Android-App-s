package com.ansh.todaysweather

import android.app.DownloadManager.Query
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.ansh.todaysweather.databinding.ActivityMain1Binding
import com.google.android.material.color.utilities.ViewingConditions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.util.Date
import java.util.Locale
import java.util.concurrent.locks.Condition


class MainActivity1 : AppCompatActivity() {
    private val binding: ActivityMain1Binding by lazy {
        ActivityMain1Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        featchWeatherdata("Mumbai")
        SearchCity()

    }

    private fun SearchCity() {
        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    featchWeatherdata(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun featchWeatherdata(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(APiInterface::class.java)
        val response = retrofit.getweatherData("Mumbai", "2cc55747a5c95a5c30f88bce4d844928", "metric")
        response.enqueue(object : Callback<weatherApp> {
            override fun onResponse(call: Call<weatherApp>, response: Response<weatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp.toString()
                    binding.temperature.text="$temperature °C"
                    val humidity=responseBody.main.humidity
                    val Wind=responseBody.wind.speed.toString()
                    val sunrise=responseBody.sys.sunrise
                    val sunset=responseBody.sys.sunset
                    val sealevel=responseBody.main.pressure
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxtemp=responseBody.main.temp_max
                    val mintemp=responseBody.main.temp_max
                    binding.condition.text=condition
                    binding.Max.text="Max temp $maxtemp °C"
                    binding.Min.text="Min temp $mintemp °C"
                    binding.humidity.text="$humidity%"
                    binding.wind.text="$Wind w/s"
                    binding.Sunrise.text="$sunrise"
                    binding.sunset1.text="$sunset"
                    binding.sealevel1.text="$sealevel hPa"
                    binding.condition.text=condition
                    binding.day.text=dayName(System.currentTimeMillis())
                        binding.date.text= date()
                        binding.cityName.text="$cityName"

                    //Log.d("TAG","onResponse:$temperature)
                    //changeImagesAccordingtoweather(condition)
                }
            }

            override fun onFailure(call: Call<weatherApp>, t: Throwable) {

            }
        })

        }


            }

     //   }
  //  }



    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy",Locale.getDefault())
        return sdf.format((Date()))

    }

    fun dayName(timestamp: Long):String {
        val sdf = SimpleDateFormat("EEEE",Locale.getDefault())
        return sdf.format((Date()))
    }


    //}


