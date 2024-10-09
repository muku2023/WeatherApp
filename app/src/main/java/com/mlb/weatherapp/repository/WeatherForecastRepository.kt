package com.mlb.weatherapp.repository

import com.mlb.weatherapp.data.ApiService
import com.mlb.weatherapp.data.datasource.WeatherForecastDataSource
import com.mlb.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(private val apiService: ApiService)  : IWeatherForecastRepository {

    override suspend fun getWeatherForecast(zipCode: String, weatherForecastDataSource: WeatherForecastDataSource): Flow<WeatherResponse> {
        return flow {
            emit(weatherForecastDataSource.getWeatherForecast(zipCode, "US"))
        }.map {  it }
    }


}