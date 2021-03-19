package com.wesleyelliott.weather.ui.choose

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wesleyelliott.weather.ui.common.WeatherSelectFlow

@Composable
fun ChooseScreen() {
    val config = LocalConfiguration.current
    val viewModel = viewModel<ChooseViewModel>()

    WeatherSelectFlow(
        isVertical = config.orientation == Configuration.ORIENTATION_PORTRAIT,

        collapsedSize = 120.dp
    ) {
        item { boxState ->
            SelectWeatherChoice(
                boxState = boxState,
                selectedWeather = viewModel.state.value.weatherOption,
                onChoiceSelect = {
                    viewModel.selectWeather(it)
                    next()
                }
            )
        }

        item { boxState ->
            SelectTemperatureChoice(
                boxState = boxState,
                selectedTemperatureOption = viewModel.state.value.temperatureOption,
                onChoiceSelect = {
                    viewModel.selectTemperature(it)
                    next()
                }
            )
        }

        item { boxState ->
            SelectEnvironmentChoice(
                boxState = boxState,
                selectedEnvironmentOption = viewModel.state.value.environmentOption,
                onChoiceSelect = {
                    viewModel.selectEnvironment(it)
                    next()
                }
            )
        }
    }
}