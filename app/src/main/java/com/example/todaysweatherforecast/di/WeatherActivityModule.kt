package com.example.todaysweatherforecast.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaysweatherforecast.App
import com.example.todaysweatherforecast.alert_dialog.InputDialog
import com.example.todaysweatherforecast.database.AppDatabase
import com.example.todaysweatherforecast.fragment.ProgressBarFragment
import com.example.todaysweatherforecast.mvp.main.WeatherContract
import com.example.todaysweatherforecast.mvp.main.WeatherPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherActivityModule(private val application: App) {
    @Provides
    @Singleton
    fun providesPresenter(app : AppDatabase): WeatherContract.Presenter =  WeatherPresenter(app)

    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    fun linearLayoutManager(ctx : Context) : LinearLayoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);

    @Provides
    fun providesProgressBarFragment() = ProgressBarFragment()

    @Provides
    fun inputDialog() = InputDialog()
}