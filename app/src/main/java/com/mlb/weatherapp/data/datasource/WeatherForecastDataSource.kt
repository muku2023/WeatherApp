package com.mlb.weatherapp.data.datasource

import com.mlb.weatherapp.data.ApiService
import com.mlb.weatherapp.model.WeatherResponse
import javax.inject.Inject

class WeatherForecastDataSource @Inject constructor(private val apiService: ApiService) : IWeatherForecastDataSource {

    override suspend fun getWeatherForecast(zipCode: String, country: String): WeatherResponse {

        return apiService.getWeatherForecast(zipCode = "$zipCode,$country").body()!!
    }


}