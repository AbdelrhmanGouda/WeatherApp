package com.example.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Repo.WeatherRepo
import com.example.weatherapp.entity.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepo: WeatherRepo) : ViewModel() {

    private val _weather: MutableStateFlow<WeatherResponse?> = MutableStateFlow(null)
    val weather: StateFlow<WeatherResponse?> = _weather
    fun getWeather(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                _weather.value = weatherRepo.getCurrentWeatherFromRemote(lat, lon)

                Log.d("test",_weather.value.toString())

            } catch (e: Exception) {
                Log.e("WeatherException", e.message.toString())
            }

        }

    }

}