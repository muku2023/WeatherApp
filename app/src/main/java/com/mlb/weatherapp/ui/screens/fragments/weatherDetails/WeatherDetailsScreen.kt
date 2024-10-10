package com.mlb.weatherapp.ui.screens.fragments.weatherDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mlb.weatherapp.model.WeatherInfo
import com.mlb.weatherapp.ui.components.Toolbar
import com.mlb.weatherapp.ui.theme.ThemeColor
import com.mlb.weatherapp.utility.StringUtility.Companion.getCardinalDirection
import com.mlb.weatherapp.utility.StringUtility.Companion.getWindSpeed
import com.mlb.weatherapp.utility.StringUtility.Companion.kelvinToFahrenheit
import com.mlb.weatherapp.utility.StringUtility.Companion.toTitleCase


@Composable
fun WeatherDetailsScreen(weatherInfo: String, navController: NavController)
{

    val forecastDetails = Gson().fromJson(weatherInfo, WeatherInfo::class.java)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeColor.primary()
    ) {
        Column {
            Toolbar(title = "Weather Details",
                backButtonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onBackButtonClick = {
                     navController.navigateUp() // Navigate back on click
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = forecastDetails.displayDayText,
                    style = MaterialTheme.typography.h6,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Temp: " + (kelvinToFahrenheit(forecastDetails.main.temp)).toString() + "F",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Feels Like: " + (kelvinToFahrenheit(forecastDetails.main.feels_like)).toString() + "F",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Humidity: " + forecastDetails.main.humidity + "%",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Wind Speed: " + getWindSpeed(forecastDetails.wind.speed) + " mph " + getCardinalDirection(
                        forecastDetails.wind.deg
                    ), style = MaterialTheme.typography.h5, color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = forecastDetails.weather[0].description.toTitleCase(),
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )

            }
        }
    }
}


