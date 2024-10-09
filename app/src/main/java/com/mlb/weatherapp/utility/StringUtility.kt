package com.mlb.weatherapp.utility

import android.annotation.SuppressLint
import androidx.compose.ui.text.capitalize
import java.util.Locale
import kotlin.math.round

class StringUtility {

    companion object {

        @SuppressLint("DefaultLocale")
        fun String.toTitleCase(): String =
            split(" ").joinToString(" ") {
                it.lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }

        fun kelvinToCelsius(kelvin: Double): Double {
            return kelvin - 273.15
        }

        fun kelvinToFahrenheit(kelvin: Double): Int {
            return round((kelvin - 273.15) * 9 / 5 + 32).toInt()
        }

        fun getCardinalDirection(degrees: Int): String {
            return when {
                degrees < 0 || degrees > 360 -> ""
                degrees >= 0 && degrees < 22.5 -> "N"
                degrees < 67.5 -> "NE"
                degrees < 112.5 -> "E"
                degrees < 157.5 -> "SE"
                degrees < 202.5 -> "S"
                degrees < 247.5 -> "SW"
                degrees < 292.5 -> "W"
                degrees < 337.5 -> "NW"
                else -> "N"
            }
        }

        fun getWindSpeed(speed: Double): String {
            return (speed * 2.23694).toInt().toString()
        }

    }
}