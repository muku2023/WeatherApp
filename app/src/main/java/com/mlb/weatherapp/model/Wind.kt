package com.mlb.weatherapp.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Wind(
    val speed: Double,  // Wind speed
    val deg: Int,       // Wind direction in degrees
    val gust: Double    // Wind gust speed
) : Parcelable