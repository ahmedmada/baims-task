package com.ahmed.baimstask.data.model.mapper

import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.data.model.CityResponseItem

fun CityResponseItem.toCity(): City {
    return City(
        id = this.id,
        cityNameAr = this.cityNameAr,
        cityNameEn = this.cityNameEn,
        lat = this.lat,
        lon = this.lon
    )
}
