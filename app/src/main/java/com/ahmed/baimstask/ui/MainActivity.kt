package com.ahmed.baimstask.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.baimstask.R
import com.ahmed.baimstask.data.model.City
import com.ahmed.baimstask.databinding.ActivityMainBinding
import com.ahmed.baimstask.util.Resource
import com.ahmed.baimstask.util.toGone
import com.ahmed.baimstask.util.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherAdapter = WeatherAdapter()

        binding.weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }

        observeCities()
        observeWeatherData()

        binding.retry.setOnClickListener {
            loading()
            viewModel.fetchCities()
        }
    }

    private fun loading() {
        binding.noData.toGone()
        binding.notAccurate.toGone()
        binding.retry.toGone()
        weatherAdapter.submitList(arrayListOf())
        binding.progressBar.toVisible()
    }

    private fun initSpinner(cities: List<City>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.citySpinner.adapter = adapter

        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCity = parent.getItemAtPosition(position).toString()
                loading()
                viewModel.getWeatherData(cities[position].id, cities[position].lat, cities[position].lon)
                Toast.makeText(this@MainActivity, getString(R.string.selected, selectedCity), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun observeCities() {
        viewModel.cities.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    loading()
                }
                is Resource.Success -> {
                    binding.progressBar.toGone()
                    resource.data.let { initSpinner(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.toGone()
                    if (resource.data.isEmpty()) {
                        binding.noData.toVisible()
                        binding.retry.toVisible()
                    } else {
                        initSpinner(resource.data)
                    }
                }
            }
        }
    }

    private fun observeWeatherData() {
        viewModel.weather.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    loading()
                }
                is Resource.Success -> {
                    binding.progressBar.toGone()
                    resource.data.let { weatherAdapter.submitList(it) }
                    binding.noData.toGone()
                    binding.notAccurate.toGone()
                    binding.retry.toGone()
                }
                is Resource.Error -> {
                    binding.progressBar.toGone()
                    resource.data.let { weatherAdapter.submitList(it) }
                    if (resource.data.isEmpty()) {
                        binding.noData.toVisible()
                        binding.retry.toVisible()
                        binding.notAccurate.toGone()
                    } else {
                        binding.notAccurate.toVisible()
                        binding.retry.toGone()
                        binding.noData.toGone()
                    }
                }
            }
        }
    }
}
