package com.ahmed.baimstask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.baimstask.data.model.WeatherData
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table WHERE cityId = :cityId")
    fun getWeatherByCityId(cityId: Int): List<WeatherData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherData: List<WeatherData>)
}
