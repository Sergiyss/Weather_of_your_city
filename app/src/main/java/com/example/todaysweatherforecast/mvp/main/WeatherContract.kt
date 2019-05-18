package com.example.todaysweatherforecast.mvp.main

import android.location.LocationManager
import com.example.todaysweatherforecast.base.BaseView
import com.example.todaysweatherforecast.data_weather.Weather
import com.example.todaysweatherforecast.mvp.BaseMvpPresenter

interface WeatherContract {
    interface View : BaseView {
        fun displayWeatherInformation(nameCity : String, list: Weather)
        //Отобразить все города
        fun showWeatherAllCities(arr : List<Weather>)
        //Скрываем прогресс бар, перед загрузкой rv списка всех городов.
        fun goneProgressBarLinearRV()
    }
    interface Presenter : BaseMvpPresenter<View> {
        //Передает расположения устройства и флаг на обновления информации.
        fun getObtainNameCity(locationManager: LocationManager, updateLocations : Boolean)
        //Добавление нового города в список.
        fun addNewCityList(nameCity: String)
        //Начать загрузку всех городов из базы данных для отображения в rv
        fun loadCity(nameCity: String)
        //Выгружаем список городов
        fun getCityUser(): List<Weather>
        //начинаем загружать все доступные города из списка пользователя.
        fun startLoadAllCityes()
    }
}