package com.ahmed.baimstask.domain.usecase

import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(cityId: Int, lat: Double, lon: Double): Flow<Resource<List<WeatherData>>> {
        return repository.getWeatherData(cityId, lat, lon)
    }
}
