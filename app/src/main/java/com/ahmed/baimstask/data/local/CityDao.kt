package com.ahmed.baimstask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmed.baimstask.data.model.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<City>)

    @Query("SELECT * FROM city_table")
    fun getAllCities(): List<City>
}
