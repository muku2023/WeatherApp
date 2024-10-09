package com.mlb.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class WeatherInfo(

    @SerialName("dt")
    val dt: Long,
    @SerialName("main")
    val main: Main,
    @SerialName("weather")
    val weather: List<WeatherDetails>,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val dt_txt: String,
    var displayDayText: String

) : Parcelable
