package com.mlb.weatherapp.ui.screens.fragments.weatherList

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mlb.weatherapp.ui.screens.viewmodel.WeatherForecastListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastListFragment : Fragment() {

    private val viewModel: WeatherForecastListViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                //WeatherForecastSearch(viewModel = viewModel, navController = findNavController())
                WeatherApp(viewModel = viewModel)
            }
        }
    }

}


