package com.ahmed.baimstask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.data.model.WeatherData
import com.ahmed.baimstask.domain.usecase.GetCitiesUseCase
import com.ahmed.baimstask.domain.usecase.GetWeatherDataUseCase
import com.ahmed.baimstask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _cities = MutableLiveData<Resource<List<City>>>()
    val cities: LiveData<Resource<List<City>>> get() = _cities

    private val _weather = MutableLiveData<Resource<List<WeatherData>>>()
    val weather: LiveData<Resource<List<WeatherData>>> get() = _weather

    init {
        fetchCities()
    }

    fun fetchCities() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getCitiesUseCase().collect { resource ->
                    _cities.postValue(resource)
                }
            }
        }
    }

    fun getWeatherData(cityId: Int, lat: Double, lon: Double) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getWeatherDataUseCase(cityId, lat, lon).collect { resource ->
                    _weather.postValue(resource)
                }
            }
        }
    }
}
