package com.wesleyelliott.weather.ui.choose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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

    private fun setState(reducer: WeatherChoice.() -> WeatherChoice) {
        weatherChoice = reducer(weatherChoice)
        _state.value = weatherChoice
    }
}