package com.mlb.weatherapp.model

import kotlinx.serialization.Serializable


@Serializable
data class WeatherResponse(

    val list: List<WeatherInfo>

)
