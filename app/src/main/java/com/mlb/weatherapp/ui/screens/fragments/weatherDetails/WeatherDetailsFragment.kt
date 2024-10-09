package com.mlb.weatherapp.ui.screens.fragments.weatherDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class WeatherDetailsFragment: Fragment() {

    private val viewModel: WeatherDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val forecastData = arguments?.let { WeatherDetailsFragmentArgs.fromBundle(it).forecastData}
            setContent {
                if (forecastData != null)
                    WeatherDetailsScreen(forecastData)
            }
        }
    }

}