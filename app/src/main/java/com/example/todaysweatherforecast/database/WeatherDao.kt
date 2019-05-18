package com.example.todaysweatherforecast.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * from weather_table ORDER BY nameCity ASC")
    fun getAllWords(): List<WeatherTable>

    @Insert
    suspend  fun insert(word: WeatherTable)

    @Query("DELETE FROM weather_table")
    fun deleteAll()

}