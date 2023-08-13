package com.example.weatherapp.entity


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: ArrayList<WeatherList> = arrayListOf(),
    val city: City
)