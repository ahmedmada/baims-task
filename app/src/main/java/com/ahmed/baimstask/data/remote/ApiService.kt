package com.ahmed.baimstask.data.remote

import com.ahmed.baimstask.data.model.CityResponse
import com.ahmed.baimstask.data.model.WeatherResponse
import com.ahmed.baimstask.util.Constants.API_KEY
import com.ahmed.baimstask.util.Constants.CITIES_JSON_URL
import com.ahmed.baimstask.util.Constants.FORECAST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(CITIES_JSON_URL)
    suspend fun getCities(): Response<CityResponse>

    @GET(FORECAST)
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = API_KEY
    ): Response<WeatherResponse>
}