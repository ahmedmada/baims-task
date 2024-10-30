package com.ahmed.baimstask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.data.model.WeatherData

@Database(entities = [City::class, WeatherData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
}
