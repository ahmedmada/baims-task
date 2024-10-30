package com.ahmed.baimstask

import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.domain.repositry.WeatherRepository
import com.ahmed.baimstask.domain.usecase.GetWeatherDataUseCase
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
class GetWeatherDataUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getWeatherDataUseCase: GetWeatherDataUseCase

    private val testWeatherData = listOf(
        WeatherData(date = "2024-10-28", cityId = 1, description = "Sunny"),
        WeatherData(date = "2024-10-29", cityId = 1, description = "Cloudy")
    )

    @Before
    fun setUp() {
        weatherRepository = mock()
        getWeatherDataUseCase = GetWeatherDataUseCase(weatherRepository)
    }

    @Test
    fun `invoke should return weather data when repository returns success`() = runTest {
        // Mock repository to return a successful flow
        val flow: Flow<Resource<List<WeatherData>>> = flow {
            emit(Resource.Success(testWeatherData))
        }
        whenever(weatherRepository.getWeatherData(1, 25.276987, 55.296249)).thenReturn(flow)

        // Execute the use case
        val result = getWeatherDataUseCase(1, 25.276987, 55.296249)

        // Collect and verify the data
        result.collect { resource ->
            when (resource) {
                is Resource.Success -> assertEquals(testWeatherData, resource.data)
                else -> fail("Expected Resource.Success but got $resource")
            }
        }
    }

    @Test
    fun `invoke should return error when repository returns error`() = runTest {
        // Mock repository to return an error flow
        val flow: Flow<Resource<List<WeatherData>>> = flow {
            emit(Resource.Error("Failed to fetch weather data", arrayListOf()))
        }
        whenever(weatherRepository.getWeatherData(1, 25.276987, 55.296249)).thenReturn(flow)

        // Execute the use case
        val result = getWeatherDataUseCase(1, 25.276987, 55.296249)

        // Collect and verify the error
        result.collect { resource ->
            when (resource) {
                is Resource.Error -> assertEquals("Failed to fetch weather data", resource.message)
                else -> fail("Expected Resource.Error but got $resource")
            }
        }
    }
}
