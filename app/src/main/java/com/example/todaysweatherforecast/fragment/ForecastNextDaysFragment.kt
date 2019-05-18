package com.example.todaysweatherforecast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todaysweatherforecast.R
import com.example.todaysweatherforecast.adapter_rv.ForecastNextDaysAdapter
import com.example.todaysweatherforecast.base.BaseRvFragment
import com.example.todaysweatherforecast.data_weather.Forecastday

class ForecastNextDaysFragment(val list : List<Forecastday>) : BaseRvFragment() {

    private lateinit var recyclerView: RecyclerView

    override fun init(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.fragment_forecast_next_days, container, false)

        recyclerView = root.findViewById(R.id.rvForecastNextDaysFragment)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        initRV(mRecyclerview = recyclerView, mLayoutManager = linearLayoutManager)

        return root
    }

    override fun setAdapter(): RecyclerView.Adapter<*> = ForecastNextDaysAdapter(list)
}