package com.example.todaysweatherforecast.adapter_rv

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.todaysweatherforecast.R
import com.example.todaysweatherforecast.data_weather.Forecastday

class ForecastNextDaysAdapter(data: List<Forecastday>) : BaseQuickAdapter<Forecastday, BaseViewHolder>(
    R.layout.item_forecast_next_days, data) {

    override fun convert(helper: BaseViewHolder, item: Forecastday) {

        helper
            .setText(R.id.itemWeatherForecastDate, item.date)
            .setText(R.id.itemWeatherConditionText, item.day.condition.text)
            .setText(R.id.itemWeatherMinTemp, "${item.day.mintempC}°")
            .setText(R.id.itemWeatherMaxTemp, "${item.day.maxtempC}°")
            .setText(R.id.itemWeatherMaxwind, "Мах. скорость ветра ${item.day.maxwindMph} м/с")

        Glide.with(mContext).load("http:${item.day.condition.icon}")
            .into(helper.getView(R.id.itemWeatherIcon) as ImageView)
    }
}