package com.ahmed.baimstask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherData(
    @PrimaryKey val date: String,
    val cityId: Int,
    val description: String
)
