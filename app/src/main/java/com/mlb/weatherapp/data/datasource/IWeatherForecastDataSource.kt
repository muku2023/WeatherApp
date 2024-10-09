package com.mlb.weatherapp.data.datasource

import com.mlb.weatherapp.model.WeatherResponse

interface IWeatherForecastDataSource {

    suspend fun getWeatherForecast(zipCode: String, country: String): WeatherResponse

}