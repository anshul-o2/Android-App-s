package com.ansh.todaysweather
import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APiInterface {
    @GET("weather")
    fun getweatherData(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ):retrofit2.Call<weatherApp>

}