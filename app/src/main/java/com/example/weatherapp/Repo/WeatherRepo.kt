package com.example.weatherapp.Repo

import com.example.weatherapp.entity.WeatherResponse
import com.example.weatherapp.service.ApiService
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun getCurrentWeatherFromRemote(lat: String, lon: String):WeatherResponse = apiService.getCurrentWeather(lat, lon)

    suspend fun getWeatherByCityFromRemote(city: String):WeatherResponse = apiService.getWeatherByCity(city)


}