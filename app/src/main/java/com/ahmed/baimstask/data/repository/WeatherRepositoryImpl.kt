package com.ahmed.baimstask.data.repository

import com.ahmed.baimstask.data.local.CityDao
import com.ahmed.baimstask.data.local.WeatherDao
import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.data.model.mapper.toCity
import com.ahmed.baimstask.data.model.mapper.toWeatherData
import com.ahmed.baimstask.data.remote.ApiService
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getCities(): Flow<Resource<List<City>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getCities()
            if (response.isSuccessful) {
                response.body()?.let { cityResponse ->
                    val cities = cityResponse.cities.map { it.toCity() }
                    cityDao.insertCities(cities)
                    emit(Resource.Success(cities))
                }
            } else {
                val cachedCities = cityDao.getAllCities()
                emit(Resource.Error("Failed to load cities from API", cachedCities))
            }
        } catch (e: Exception) {
            val cachedCities = cityDao.getAllCities()
            emit(Resource.Error("Network error: showing cached data", cachedCities))
        }
    }

    override fun getWeatherData(
        cityId: Int,
        lat: Double,
        lon: Double
    ): Flow<Resource<List<WeatherData>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getWeatherData(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val weatherDataList = weatherResponse.list.map { it.toWeatherData(cityId) }
                    weatherDao.insertWeatherData(weatherDataList)
                    emit(Resource.Success(weatherDataList))
                }
            } else {
                val cachedWeatherData = weatherDao.getWeatherByCityId(cityId)
                emit(Resource.Error("Failed to load weather data from API", cachedWeatherData))
            }
        } catch (e: Exception) {
            val cachedWeatherData = weatherDao.getWeatherByCityId(cityId)
            emit(Resource.Error("Network error: showing cached data", cachedWeatherData))
        }
    }
}
