package com.mlb.weatherapp.data

import com.mlb.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("zip") zipCode: String,
        @Query("appid") apiKey: String = ApiUrl.API_KEY,
        @Header("Cache-Control") cacheControl: String = "max-age=3600" // Cache for 1 hour
    ) : Response<WeatherResponse>
}