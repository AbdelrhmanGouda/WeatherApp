package com.example.weatherapp.service

import com.example.weatherapp.Constant.API_KEY
import com.example.weatherapp.entity.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = API_KEY,
    ):WeatherResponse

    @GET("forecast?")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") appid: String = API_KEY
    ):WeatherResponse

}