package com.example.weatherapp

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.entity.WeatherList
import com.example.weatherapp.viewModel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var cityName: TextView
    private lateinit var tempMain: TextView
    private lateinit var descMain: TextView
    private lateinit var dateMain: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        cityName = findViewById(R.id.city_name)
        tempMain = findViewById(R.id.temp_main)
        descMain = findViewById(R.id.desc_main)
        dateMain = findViewById(R.id.date_main)
        recyclerView=findViewById(R.id.recycler)
        adapter= WeatherAdapter()

        lifecycleScope.launch {
            viewModel.weather.collect {
                if (it != null) {
                    cityName.text = it.city.name

                    val temperatureFahrenheit = it.list.get(0).main?.temp
                    val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
                    val temperatureFormatted = String.format("%.2f", temperatureCelsius)
                    tempMain.text = "$temperatureFormatted °C"

                    descMain.text=it.list.get(0).weather.get(0).description

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val date = inputFormat.parse(it.list.get(0).dtTxt!!)
                    val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
                    val dateanddayname = outputFormat.format(date!!)
                    dateMain.text=dateanddayname


                    val setNewlist = it.list as List<WeatherList>
                    adapter.setList(setNewlist)
                    recyclerView.adapter=adapter

                }
            }

        }


    }

    fun getLocation(){
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
            return
        }
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null){
                val textLatitude = it.latitude.toString()
                val textLongitude = it.longitude.toString()
                viewModel.getWeather(textLatitude,textLongitude)
            }
        }



    }


    override fun onStart() {
        super.onStart()
        getLocation()
    }


}
