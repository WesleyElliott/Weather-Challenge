/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wesleyelliott.weather.ui.choose

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.ui.choose.options.SelectDistanceChoice
import com.wesleyelliott.weather.ui.choose.options.SelectEnvironmentChoice
import com.wesleyelliott.weather.ui.choose.options.SelectTemperatureChoice
import com.wesleyelliott.weather.ui.choose.options.SelectWeatherChoice
import com.wesleyelliott.weather.ui.common.AccordionLayout
import com.wesleyelliott.weather.ui.common.AccordionLayoutState
import com.wesleyelliott.weather.ui.common.rememberAccordionState
import com.wesleyelliott.weather.ui.utils.isVertical

@Composable
fun ChooseScreen(
    modifier: Modifier = Modifier,
    accordionLayoutState: AccordionLayoutState = rememberAccordionState(),
    onGoClick: (WeatherChoice) -> Unit
) {
    val viewModel = viewModel<ChooseViewModel>()
    val clicked = remember {
        mutableStateOf(false)
    }
    val fade = animateFloatAsState(
        targetValue = if (clicked.value) 0f else 1f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    BoxWithConstraints() {
        AccordionLayout(
            isVertical = isVertical,
            accordionLayoutState = accordionLayoutState,
            collapsedSize = if (isVertical) maxHeight / 5.5f else maxWidth / 5f
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
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(fade.value)
                ) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(100.dp),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            onGoClick(viewModel.state.value)
                            clicked.value = true
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
