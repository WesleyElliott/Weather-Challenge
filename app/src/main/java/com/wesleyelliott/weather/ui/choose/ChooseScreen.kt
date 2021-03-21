package com.wesleyelliott.weather.ui.choose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.ui.choose.options.SelectDistanceChoice
import com.wesleyelliott.weather.ui.choose.options.SelectEnvironmentChoice
import com.wesleyelliott.weather.ui.choose.options.SelectTemperatureChoice
import com.wesleyelliott.weather.ui.choose.options.SelectWeatherChoice
import com.wesleyelliott.weather.ui.common.AccordionLayout
import com.wesleyelliott.weather.ui.theme.baseBlue
import com.wesleyelliott.weather.ui.utils.isVertical

@Composable
fun ChooseScreen(
    onGoClick: (WeatherChoice) -> Unit
) {
    val viewModel = viewModel<ChooseViewModel>()
    BoxWithConstraints {
        AccordionLayout(
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

            item {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = baseBlue
                        ),
                        onClick = {
                            onGoClick(viewModel.state.value)
                        }
                    ) {
                        Text(
                            text = "Go!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
            }
        }
    }
}