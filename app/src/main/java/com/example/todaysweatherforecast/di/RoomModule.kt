package com.example.todaysweatherforecast.di

import android.content.Context
import com.example.todaysweatherforecast.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) :  AppDatabase =
        AppDatabase.getInstance(context)

}