package com.example.weatherapp.entity


import com.google.gson.annotations.SerializedName

data class WeatherList (
    val dt: Int,
    val main: Main,
    val weather: ArrayList<Weather> = arrayListOf(),
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Int,
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String
)