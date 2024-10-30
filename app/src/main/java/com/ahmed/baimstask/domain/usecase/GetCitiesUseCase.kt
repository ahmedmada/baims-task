package com.ahmed.baimstask.domain.usecase

import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(): Flow<Resource<List<City>>> {
        return repository.getCities()
    }
}
