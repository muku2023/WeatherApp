package com.mlb.weatherapp.utility

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class DateTimeUtility
{

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun convertUtcToLocal(utcSeconds: Long): String {
            // Convert the UTC timestamp to Instant
            val instant = Instant.ofEpochSecond(utcSeconds)

            // Get the system's default time zone
            val zoneId = ZoneId.systemDefault()

            // Convert the Instant to LocalDateTime in the system's time zone
            val localDateTime = LocalDateTime.ofInstant(instant, zoneId)

            // Format the date to a readable string (optional)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            return localDateTime.format(formatter)
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun convertStringToDateTime(dateTimeString: String): LocalDateTime {
            // Define the format of your input string
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            // Parse the string to LocalDateTime
            return LocalDateTime.parse(dateTimeString, formatter)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTimeOfDay(time: LocalTime): String {
            return when {
                time.isAfter(LocalTime.of(12, 0)) && time.isBefore(
                    LocalTime.of(
                        18,
                        0
                    )
                ) -> "Afternoon"

                time.isAfter(LocalTime.of(18, 0)) || time.isBefore(LocalTime.of(6, 0)) -> "Night"
                else -> "Morning"
            }
        }
    }
}
