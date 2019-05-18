package com.example.todaysweatherforecast.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("forecast.json")

    fun apiApixuCom(
        @Query("key") apiKey : String,
        @Query("q") city : String,
        @Query("days") days : String="7",
        @Query("lang") lang : String="ru"
    ): Call<com.example.todaysweatherforecast.data_weather.Weather>
}