package com.example.todaysweatherforecast.adapter_rv
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.todaysweatherforecast.R
import com.example.todaysweatherforecast.data_weather.Weather

class WeatherAdapter(data: List<Weather>) : BaseQuickAdapter<Weather, BaseViewHolder>(
    R.layout.item_weather, data) {

    override fun convert(helper: BaseViewHolder, item: Weather) {

        helper
            .setText(R.id.itemWeatherNameCity, item.location.name)
            .setText(R.id.itemWeatherMinTemp, "${item.forecast.forecastday[0].day.mintempC}°")
            .setText(R.id.itemWeatherMaxTemp, "${item.forecast.forecastday[0].day.maxtempC}°")
            .setText(R.id.itemWeatherThisTemp, "${item.current.tempC}°")

        Glide.with(mContext).load("http:${item.current.condition.icon}")
            .into(helper.getView(R.id.itemWeatherIcon) as ImageView)
    }

}