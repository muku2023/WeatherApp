package com.mlb.weatherapp.di

import com.mlb.weatherapp.data.ApiService
import com.mlb.weatherapp.data.datasource.WeatherForecastDataSource
import com.mlb.weatherapp.repository.WeatherForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherForecastRepository(apiService: ApiService): WeatherForecastRepository {
        return WeatherForecastRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherForecastDataSource(apiService: ApiService): WeatherForecastDataSource {
        return WeatherForecastDataSource(apiService)
    }

}