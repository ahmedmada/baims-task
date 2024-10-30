package com.ahmed.baimstask.data.model

data class WeatherResponse(
    val list: List<WeatherForecast>
)

data class WeatherForecast(
    val dt_txt: String,
    val weather: List<WeatherDescription>
)

data class WeatherDescription(
    val description: String
)
