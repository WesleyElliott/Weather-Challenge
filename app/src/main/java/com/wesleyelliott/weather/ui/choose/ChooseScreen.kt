package com.wesleyelliott.weather.ui.choose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wesleyelliott.weather.ui.common.WeatherSelectFlow
import com.wesleyelliott.weather.ui.utils.isVertical

@Composable
fun ChooseScreen() {
    val viewModel = viewModel<ChooseViewModel>()
    BoxWithConstraints {
        WeatherSelectFlow(
            isVertical = isVertical,
            collapsedSize = if (isVertical) maxHeight / 5 else maxWidth / 5
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
                SelectEnvironmentChoice(
                    boxState = boxState,
                    selectedEnvironmentOption = viewModel.state.value.environmentOption,
                    onChoiceSelect = {
                        viewModel.selectEnvironment(it)
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
                SelectDistanceChoice(
                    boxState = boxState,
                    selectedDistanceOption = viewModel.state.value.distanceOption,
                    onChoiceSelect = {
                        viewModel.selectDistance(it)
                        next()
                    }
                )
            }
        }
    }
}