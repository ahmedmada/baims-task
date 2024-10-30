package com.ahmed.baimstask.data.model

data class CityResponse(
    val cities: List<CityResponseItem>
)

data class CityResponseItem(
    val id: Int,
    val cityNameAr: String,
    val cityNameEn: String,
    val lat: Double,
    val lon: Double
)
