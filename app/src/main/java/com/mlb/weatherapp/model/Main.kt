package com.mlb.weatherapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class Main(

    @SerialName("temp")
    val temp: Double,
    @SerialName("feels_like")
    val feels_like: Double,
    @SerialName("temp_min")
    val temperatureMin: Double,
    @SerialName("temp_max")
    val temperatureMax: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val groundLevel: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("temp_kf")
    val temperatureKf: Double

) : Parcelable

