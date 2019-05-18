package com.example.todaysweatherforecast.mvp.main

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.example.todaysweatherforecast.App
import com.example.todaysweatherforecast.Coroutines
import com.example.todaysweatherforecast.data_weather.Weather
import com.example.todaysweatherforecast.database.AppDatabase
import com.example.todaysweatherforecast.database.WeatherTable
import com.example.todaysweatherforecast.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class WeatherPresenter @Inject constructor(var room : AppDatabase) : BasePresenter<WeatherContract.View>(),
    WeatherContract.Presenter {

    private var thisNameCity = ""
    private val key = "13a67b76e2b94583896121513190605"
    private var updateLocation = false
    private val arrayWeather : ArrayList<Weather> = ArrayList()

    //Получаем прогноз погоды остальных городов
    private fun getWeatherForecasts(nameCity : String): Call<Weather> =
        App.get().connectServer()!!.apiApixuCom(key,nameCity)

    @SuppressLint("MissingPermission")
    override fun getObtainNameCity(locationManager: LocationManager, updateLocations : Boolean) {
       // getWeatherForecast("Павлоград") // testing <<<<<<<<<<<<<----------------
        this.updateLocation = updateLocations

        val geocoder = Geocoder(view!!.getContext(), Locale.getDefault())
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                //Возвращаем название города.
                thisNameCity = geocoder.getFromLocation(location.latitude, location.longitude, 1)[0].locality

                if(updateLocation){
                    getWeatherForecast(thisNameCity) //Callback вызывается часто.
                    updateLocation = false
                }
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f,locationListener)
    }
    
    //Получаем прогроз погоды, для главного экрана.
    private fun getWeatherForecast(nameCity : String){
        Coroutines.ioThenMain({
            getWeatherForecasts(nameCity)
        }){
            it!!.enqueue(object : Callback<Weather> {
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    println("ERROR RETROFIT 2 " + t.message)
                }
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    view!!.displayWeatherInformation(nameCity, response.body()!!)
                }
            })
        }
        
    }
    
    override fun addNewCityList(nameCity: String) {
        //Это вынужденная проверка, срабатывает тогда, когда пользователь просматривает список доступных городов
        //И хочет добавить еще один, и он должен отобразиться в списке.
        if (arrayWeather.size != 0){
            arrayWeather.clear() //Удаляем список.
        }
        //Делаем ограничение, если приложение будет популярным. Ибо, Api ограничены.
        val room = room.weatherDao()

        Coroutines.io{
            if(room.getAllWords().size < 6){
                room.insert(WeatherTable(nameCity))
            }
        }
    }

    override fun startLoadAllCityes(){
        Coroutines.io{
            val arrNameCitys = room.weatherDao().getAllWords()
            if(arrNameCitys.size == 0){
                loadCity(null.toString())
            }

            if (arrayWeather.size == 0){
                for(item in room.weatherDao().getAllWords()){
                    println("loadAllCityes --- ${item.nameCity}")
                    loadCity(item.nameCity)
                    // return arrayWeathers
                }
            }
        }
    }

    //Отображаем списки городов.
    override fun loadCity(nameCity: String) {

        Coroutines.ioThenMain({
            getWeatherForecasts(nameCity)
        }) {
            it!!.enqueue(object : Callback<Weather> {
                override fun onFailure(call: Call<Weather>, t: Throwable) {}

                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    //Будем отбражать все доступные города и информацию о погоду о городе.

                    if(response.body() != null){
                       val weather = response.body()!!
                        arrayWeather.add(weather)
                    }

                    view!!.showWeatherAllCities(arrayWeather)
                }
            })
        }
    }

    override fun getCityUser(): ArrayList<Weather> =  arrayWeather

}