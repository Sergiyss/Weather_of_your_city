package com.example.todaysweatherforecast.data_weather

data class CurrentWeather (
    val location: Location,
    val current: Current,
    val forecast: Forecast )
