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

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wesleyelliott.weather.data.DistanceOption
import com.wesleyelliott.weather.data.EnvironmentOption
import com.wesleyelliott.weather.data.TemperatureOption
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.data.WeatherOption

class ChooseViewModel : ViewModel() {
    private var weatherChoice = WeatherChoice()
    private val _state = mutableStateOf(weatherChoice)
    val state: State<WeatherChoice> = _state

    fun selectWeather(weatherOption: WeatherOption) {
        setState {
            copy(weatherOption = weatherOption)
        }
    }

    fun selectTemperature(temperatureOption: TemperatureOption) {
        setState {
            copy(
                temperatureOption = temperatureOption
            )
        }
    }

    fun selectEnvironment(environmentOption: EnvironmentOption) {
        setState {
            copy(
                environmentOption = environmentOption
            )
        }
    }

    fun selectDistance(distanceOption: DistanceOption) {
        setState {
            copy(
                distanceOption = distanceOption
            )
        }
    }

    private fun setState(reducer: WeatherChoice.() -> WeatherChoice) {
        weatherChoice = reducer(weatherChoice)
        _state.value = weatherChoice
    }
}
