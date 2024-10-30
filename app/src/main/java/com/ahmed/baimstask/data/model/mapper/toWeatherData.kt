package com.ahmed.baimstask.data.model.mapper

import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.data.model.WeatherForecast

fun WeatherForecast.toWeatherData(cityId: Int): WeatherData {
    return WeatherData(
        date = this.dt_txt,
        cityId = cityId,
        description = this.weather.firstOrNull()?.description ?: "No description"
    )
}
