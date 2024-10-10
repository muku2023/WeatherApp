package com.mlb.weatherapp.ui.screens.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlb.weatherapp.data.datasource.WeatherForecastDataSource
import com.mlb.weatherapp.model.WeatherInfo
import com.mlb.weatherapp.model.WeatherResponse
import com.mlb.weatherapp.repository.WeatherForecastRepository
import com.mlb.weatherapp.ui.base.UiState
import com.mlb.weatherapp.utility.DateTimeUtility.Companion.convertStringToDateTime
import com.mlb.weatherapp.utility.DateTimeUtility.Companion.getTimeOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import com.mlb.weatherapp.utility.StringUtility.Companion.toTitleCase


@HiltViewModel
class WeatherForecastListViewModel @Inject constructor(private val weatherForecastRepository: WeatherForecastRepository, private val weatherForecastDataSource: WeatherForecastDataSource) : ViewModel() {

    private val _weatherForecasts = MutableStateFlow<UiState<WeatherResponse>>(UiState.Loading)

    val weatherForecasts: StateFlow<UiState<WeatherResponse>> = _weatherForecasts

    fun fetchWeatherForecast(zipCode: String) {
        viewModelScope.launch {
            weatherForecastRepository.getWeatherForecast(zipCode, weatherForecastDataSource).catch { e ->
                _weatherForecasts.value = UiState.Error(e.toString())
            }
                .collect {
                    _weatherForecasts.value = UiState.Success(it)
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun filterWeatherForecasts(forecasts: List<WeatherInfo>) : List<WeatherInfo> {

        val mutableForecastsMap: MutableList<WeatherInfo> = mutableListOf()
        forecasts.forEach { forecast ->
            run {
                val forecastDate: LocalDateTime = convertStringToDateTime(forecast.dt_txt)
                val day:String  = forecastDate.dayOfWeek.name
                val time = forecastDate.toLocalTime().toString()
                forecast.displayDayText = getTimeOfDay(forecastDate.toLocalTime())
                if (forecastDate.toLocalDate() == LocalDateTime.now().toLocalDate()) {
                    if (time == "15:00") {
                        forecast.displayDayText = "This " + forecast.displayDayText
                        mutableForecastsMap.add(forecast)
                    } else if (time == "21:00") {
                        forecast.displayDayText = "Tonight"
                        mutableForecastsMap.add(forecast)
                    }
                } else {
                    if (time == "06:00") {
                        forecast.displayDayText = day.toTitleCase()
                        mutableForecastsMap.add(forecast)
                    } else if (time == "21:00") {
                        forecast.displayDayText = day.toTitleCase() + " " + forecast.displayDayText
                        mutableForecastsMap.add(forecast)
                    }
                }
            }

        }

        return mutableForecastsMap
    }

}