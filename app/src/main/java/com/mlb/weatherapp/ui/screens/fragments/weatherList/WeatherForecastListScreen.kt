package com.mlb.weatherapp.ui.screens.fragments.weatherList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.mlb.weatherapp.model.Main
import com.mlb.weatherapp.model.WeatherDetails
import com.mlb.weatherapp.model.WeatherInfo
import com.mlb.weatherapp.model.Wind
import com.mlb.weatherapp.ui.base.UiState
import com.mlb.weatherapp.ui.components.Toolbar
import com.mlb.weatherapp.ui.screens.fragments.weatherDetails.WeatherDetailsScreen
import com.mlb.weatherapp.ui.theme.ThemeColor
import com.mlb.weatherapp.ui.screens.viewmodel.WeatherForecastListViewModel
import com.mlb.weatherapp.utility.StringUtility.Companion.getCardinalDirection
import com.mlb.weatherapp.utility.StringUtility.Companion.getWindSpeed
import com.mlb.weatherapp.utility.StringUtility.Companion.kelvinToFahrenheit
import com.mlb.weatherapp.utility.StringUtility.Companion.toTitleCase


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastSearch(viewModel: WeatherForecastListViewModel, navController: NavController) {
    var zipCode by rememberSaveable { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ThemeColor.primary()
    )
    {
        Column {
            Toolbar(title = "Search Weather Forecast")

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
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,     // Border color when focused
                        unfocusedBorderColor = Color.Gray    // Border color when not focused
                    )
                )

                Spacer(modifier = Modifier.height(16.dp)) // Spacer for some space between the TextField and Button

                // Button to initiate the search
                Button(
                    onClick = {
                        if (zipCode.isNotEmpty()) {
                            viewModel.fetchWeatherForecast(zipCode)
                            navController.navigate("weatherForecasts/$zipCode")
                        }
                    },
                    modifier = Modifier.align(Alignment.End) // Align the button to the end
                ) {
                    Text("Search")
                }

            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastsScreen(viewModel: WeatherForecastListViewModel, navController: NavController) {

    val weatherForecasts = viewModel.weatherForecasts.collectAsState()

    val forecasts = weatherForecasts.value

    Column(
        modifier = Modifier.fillMaxSize() // Fill the available screen space
    ) {
        Toolbar(title = "Weather Forecast")
        // LazyColumn should display loading, error, or success states
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
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
                                    val forecastJson = Gson().toJson(forecast)
                                    navController.navigate("weatherDetails/$forecastJson")

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherApp(viewModel: WeatherForecastListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "weatherForecastSearch") {
        composable("weatherForecastSearch") {
            WeatherForecastSearch(viewModel = viewModel, navController = navController)
        }
        composable("weatherForecasts/{zipCode}") { _ ->  // Correctly ignore
            WeatherForecastsScreen(viewModel = viewModel, navController = navController)
        }
        composable("weatherDetails/{forecastJson}") { backStackEntry ->
            val forecastJson = backStackEntry.arguments?.getString("forecastJson") ?: return@composable
            WeatherDetailsScreen(forecastJson, navController) // Pass the JSON string to your details screen
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ListItem_Preview() {
    val main = Main(1000.0,1000.0,1000.0,1000.0,1000, 1, 1, 10, 1.0 )
    val weatherDetails = WeatherDetails(1, "main", "description", "icon")
    val list:  List<WeatherDetails> = listOf( weatherDetails)
    val weatherInfo = WeatherInfo(1728572400, main, list, Wind(0.0,0,0.0), 10000, 100000.0, "2024-10-10 15:00:00", "" )

    weatherInfo.displayDayText = "Today"
    ListItem(weatherInfo, onClick = {})
}