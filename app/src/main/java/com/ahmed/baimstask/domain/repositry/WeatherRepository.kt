package com.ahmed.baimstask.domain.repositry

import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCities(): Flow<Resource<List<City>>>
    fun getWeatherData(cityId: Int, lat: Double, lon: Double): Flow<Resource<List<WeatherData>>>
}
