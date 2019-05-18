package com.example.todaysweatherforecast.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
class WeatherTable(
    @ColumnInfo(name = "nameCity") val nameCity: String
){
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}