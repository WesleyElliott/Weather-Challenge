package com.wesleyelliott.weather.data

import androidx.annotation.DrawableRes
import com.wesleyelliott.weather.R

enum class WeatherOption {
    Sunny, Rainy, Snowy, Stormy, Windy, Calm
}

enum class TemperatureOption {
    Warm, Cold, Any
}

enum class EnvironmentOption {
    Mountains, Nature, Coastal, Urban
}

enum class DistanceOption {
    _100, _200, _300, Any
}

data class WeatherChoice(
    val weatherOption: WeatherOption? = null,
    val temperatureOption: TemperatureOption? = null,
    val environmentOption: EnvironmentOption? = null,
    val distanceOption: DistanceOption? = null,
)

@DrawableRes
fun WeatherOption.getIcon(): Int {
    return when (this) {
        WeatherOption.Sunny -> R.drawable.ic_sun
        WeatherOption.Rainy -> R.drawable.ic_rain
        WeatherOption.Snowy -> R.drawable.ic_snow
        WeatherOption.Stormy -> R.drawable.ic_storm
        WeatherOption.Windy -> R.drawable.ic_wind
        WeatherOption.Calm -> R.drawable.ic_rainbow
    }
}