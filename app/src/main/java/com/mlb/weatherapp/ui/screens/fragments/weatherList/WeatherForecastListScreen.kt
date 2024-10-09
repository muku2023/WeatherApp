package com.mlb.weatherapp.ui.screens.fragments.weatherList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mlb.weatherapp.model.WeatherInfo
import com.mlb.weatherapp.ui.base.UiState
import com.mlb.weatherapp.ui.theme.ThemeColor
import com.mlb.weatherapp.ui.screens.fragments.weatherList.WeatherForecastListFragmentDirections.toWeatherDetails
import com.mlb.weatherapp.utility.StringUtility.Companion.getCardinalDirection
import com.mlb.weatherapp.utility.StringUtility.Companion.getWindSpeed
import com.mlb.weatherapp.utility.StringUtility.Companion.kelvinToFahrenheit
import com.mlb.weatherapp.utility.StringUtility.Companion.toTitleCase


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastSearch(viewModel: WeatherForecastListViewModel, navController: NavController) {
    var zipCode by remember { mutableStateOf("") }
    var showWeather by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeColor.primary()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TextField for zip code input
            OutlinedTextField(
                value = zipCode,
                onValueChange = { zipCode = it },
                label = { Text("Enter ZIP Code") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp)) // Spacer for some space between the TextField and Button

            // Button to initiate the search
            Button(
                onClick = {
                    viewModel.fetchWeatherForecast(zipCode)
                    showWeather = true
                },
                modifier = Modifier.align(Alignment.End) // Align the button to the end
            ) {
                Text("Search")
            }

            if (showWeather)
                WeatherForecastsScreen(viewModel = viewModel, navController = navController)
        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastsScreen(viewModel: WeatherForecastListViewModel, navController: NavController) {

    val weatherForecasts = viewModel.weatherForecasts.collectAsState()

    val forecasts = weatherForecasts.value

    // LazyColumn should display loading, error, or success states
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
       // contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        forecasts.let {
            when (it) {
                is UiState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            color = Color.Blue,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                is UiState.Success -> {
                    items(viewModel.filterWeatherForecasts(it.data.list)) { forecast ->
                        ListItem(
                            weatherInfo = forecast,
                            onClick = {
                                navController.navigate(toWeatherDetails(forecast))
                            }
                        )
                    }
                }

                is UiState.Error -> {
                    item {
                        Text(
                            text = "Error loading data: ${it.message}",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListItem(weatherInfo: WeatherInfo, onClick: (WeatherInfo) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(weatherInfo) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = weatherInfo.displayDayText, style = MaterialTheme.typography.h6, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = (kelvinToFahrenheit(weatherInfo.main.temp)).toString() + "F", style = MaterialTheme.typography.h5, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = getWindSpeed(weatherInfo.wind.speed) + " mph " + getCardinalDirection(weatherInfo.wind.deg), style = MaterialTheme.typography.h5, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = weatherInfo.weather[0].description.toTitleCase(), style = MaterialTheme.typography.h5, color = Color.Black)

        }
    }

}

@Preview
@Composable
fun ListItem_Preview() {
//    var weatherInfo = WeatherInfo()
//    ListItem()
}