package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.entity.Weather
import com.example.weatherapp.entity.WeatherList
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherAdapter: RecyclerView.Adapter<WeatherViewHolder>() {
    private var listOfWeather = listOf<WeatherList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_row, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfWeather.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(listOfWeather.get(position).dtTxt!!)
        val outputFormat = SimpleDateFormat("d MMMM EEEE", Locale.getDefault())
        val dateanddayname = outputFormat.format(date!!)
        holder.day.text = dateanddayname

        var len =listOfWeather.get(position).dtTxt.length
        holder.hour.text=listOfWeather.get(position).dtTxt.subSequence(10,len)

        val temperatureFahrenheit = listOfWeather.get(position).main?.temp
        val temperatureCelsius = (temperatureFahrenheit?.minus(273.15))
        val temperatureFormatted = String.format("%.2f", temperatureCelsius)
        holder.temp.text = "$temperatureFormatted °C"
        holder.humidity.text=listOfWeather.get(position).main.humidity.toString()
        holder.windSpeed.text=listOfWeather.get(position).wind.speed.toString()
    }
    fun setList(newList: List<WeatherList>) {

        this.listOfWeather = newList

    }
}

class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val day : TextView = itemView.findViewById(R.id.dayDateText)
    val humidity : TextView = itemView.findViewById(R.id.humidity)
    val temp : TextView = itemView.findViewById(R.id.tempDisplay)
    val windSpeed : TextView = itemView.findViewById(R.id.windSpeed)
    val hour : TextView = itemView.findViewById(R.id.hour)

}
