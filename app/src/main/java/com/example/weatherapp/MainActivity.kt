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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var cityName: TextView
    private lateinit var tempMain: TextView
    private lateinit var descMain: TextView
    private lateinit var dateMain: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        // Check for location permissions
//        if (checkLocationPermissions()) {
//            // Permissions are granted, proceed to get the current location
//            getCurrentLocation()
//        } else {
//            // Request location permissions
//            requestLocationPermissions()
//        }
    }
//    private fun checkLocationPermissions(): Boolean {
//        val fineLocationPermission = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//        val coarseLocationPermission = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//        return fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
//                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
//    }
//    private fun requestLocationPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ),
//            Constant.PERMISSION_REQUEST_CODE
//        )
//    }
//    // Handle the permission request result
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == Constant.PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() &&
//                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                grantResults[1] == PackageManager.PERMISSION_GRANTED
//            ) {
//                // Permissions granted, get the current location
//                getCurrentLocation()
//            } else {
//                // Permissions denied, handle accordingly
//                // For example, show an error message or disable location-based features
//            }
//        }
//    }
//
//    // Function to get the current location
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getCurrentLocation() {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            val location: Location? =
//                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//            if (location != null) {
//                val latitude = location.latitude
//                val longitude = location.longitude
//                // Use the latitude and longitude values as needed
//                // ..
//
//
//                // Example: Display latitude and longitude in logs
//
//
//
//
//
//                Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()
//
//
//                Log.d("Current Location", "Latitude: $latitude, Longitude: $longitude")
//
//                // Reverse geocode the location to get address information
//                reverseGeocodeLocation(latitude, longitude)
//            } else {
//                // Location is null, handle accordingly
//                // For example, request location updates or show an error message
//            }
//        } else {
//            // Location permission not granted, handle accordingly
//            // For example, show an error message or disable location-based features
//        }
//    }
//
//    // Function to reverse geocode the location and get address information
//    private fun reverseGeocodeLocation(latitude: Double, longitude: Double) {
//        val geocoder = Geocoder(this, Locale.getDefault())
//        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
//        if (addresses!!.isNotEmpty()) {
//            val address = addresses[0]
//            val addressLine = address.getAddressLine(0)
//            // Use the addressLine as needed
//            // ...
//            // Example: Display address in logs
//            Log.d("Current Address", addressLine)
//        } else {
//            // No address found, handle accordingly
//            // For example, show an error message or use default address values
//        }
//    }

    override fun onStart() {
        super.onStart()
        viewModel.getWeather()
    }


}
