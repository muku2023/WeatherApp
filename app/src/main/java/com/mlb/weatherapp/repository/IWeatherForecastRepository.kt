package com.mlb.weatherapp.repository

import com.mlb.weatherapp.data.datasource.WeatherForecastDataSource
import com.mlb.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherForecastRepository {

    suspend fun getWeatherForecast(zipCode: String, weatherForecastDataSource: WeatherForecastDataSource): Flow<WeatherResponse>
}