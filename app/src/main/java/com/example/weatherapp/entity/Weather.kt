package com.example.weatherapp.entity


import com.google.gson.annotations.SerializedName

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)