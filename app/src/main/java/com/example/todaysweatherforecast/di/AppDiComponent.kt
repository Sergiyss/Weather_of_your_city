package com.example.todaysweatherforecast.di

import com.example.todaysweatherforecast.WeatherActivity
import com.example.todaysweatherforecast.mvp.main.WeatherPresenter
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        WeatherActivityModule::class,
        RoomModule::class
    ]
)
@Singleton
interface AppDiComponent {
    fun inject(weatherActivity: WeatherActivity)
}