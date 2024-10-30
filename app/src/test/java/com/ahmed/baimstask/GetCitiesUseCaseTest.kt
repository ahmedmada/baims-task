package com.ahmed.baimstask

import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.domain.usecase.GetCitiesUseCase
import com.ahmed.baimstask.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetCitiesUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    private val testCities = listOf(
        City(id = 1, cityNameAr = "دبي", cityNameEn = "Dubai", lat = 25.276987, lon = 55.296249),
        City(id = 2, cityNameAr = "أبو ظبي", cityNameEn = "Abu Dhabi", lat = 24.453884, lon = 54.3773438)
    )

    @Before
    fun setUp() {
        weatherRepository = mock()
        getCitiesUseCase = GetCitiesUseCase(weatherRepository)
    }

    @Test
    fun `invoke should return cities data when repository returns success`() = runTest {
        // Mock repository to return a successful flow
        val flow: Flow<Resource<List<City>>> = flow {
            emit(Resource.Success(testCities))
        }
        whenever(weatherRepository.getCities()).thenReturn(flow)

        // Execute the use case
        val result = getCitiesUseCase()

        // Collect and verify the data
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> assertEquals(testCities, resource.data)
                else -> fail("Expected Resource.Success but got $resource")
            }
        }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Mock repository to return an error flow
        val flow: Flow<Resource<List<City>>> = flow {
            emit(Resource.Error("Failed to fetch cities",arrayListOf()))
        }
        whenever(weatherRepository.getCities()).thenReturn(flow)

        // Execute the use case
        val result = getCitiesUseCase()

        // Collect and verify the error
        result.collect { resource ->
            when (resource) {
                is Resource.Error -> assertEquals("Failed to fetch cities", resource.message)
                else -> fail("Expected Resource.Error but got $resource")
            }
        }
    }
}
