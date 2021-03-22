package com.wesleyelliott.weather.data

import android.os.Bundle
import androidx.annotation.DrawableRes
import com.wesleyelliott.weather.R

private const val WEATHER_KEY = "weather"
private const val TEMPERATURE_KEY = "temperature"
private const val ENVIRONMENT_KEY = "environment"
private const val DISTANCE_KEY = "distance"

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
    _100, _250, _500, Any
}

data class WeatherChoice(
    val weatherOption: WeatherOption? = null,
    val temperatureOption: TemperatureOption? = null,
    val environmentOption: EnvironmentOption? = null,
    val distanceOption: DistanceOption? = null,
)

data class WeatherReport(
    val location: Location,
    val distance: Int,
    val distanceUnit: DistanceUnit,
    val currentTemp: Int,
    val currentConditions: WeatherOption,
    val forecast: List<Forecast> = emptyList()
)

data class Forecast(
    val time: String,
    val conditions: WeatherOption,
    val temperature: Int
)

data class Location(
    val name: String,
    val country: String,
    val imageUrl: String
)

enum class DistanceUnit {
    Km,
    Mi
}

/**
 * Helper to convert a [WeatherChoice] to a [Bundle] for state saving
 */
fun WeatherChoice.toBundle(): Bundle {
    return Bundle().apply {
        putSerializable(WEATHER_KEY, weatherOption)
        putSerializable(TEMPERATURE_KEY, temperatureOption)
        putSerializable(ENVIRONMENT_KEY, environmentOption)
        putSerializable(DISTANCE_KEY, distanceOption)
    }
}

/**
 * Helper to parse a [Bundle] to a [WeatherChoice] for state restoring
 */
fun Bundle.fromBundle(): WeatherChoice {
    val weatherOption = getSerializable(WEATHER_KEY) as? WeatherOption
    val temperatureOption = getSerializable(TEMPERATURE_KEY) as? TemperatureOption
    val environmentOption = getSerializable(ENVIRONMENT_KEY) as? EnvironmentOption
    val distanceOption = getSerializable(DISTANCE_KEY) as? DistanceOption
    return WeatherChoice(
        weatherOption,
        temperatureOption,
        environmentOption,
        distanceOption
    )
}

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

@DrawableRes
fun TemperatureOption.getIcon(): Int {
    return when (this) {
        TemperatureOption.Warm -> R.drawable.ic_hot
        TemperatureOption.Cold -> R.drawable.ic_cold
        TemperatureOption.Any -> R.drawable.ic_any_temperature
    }
}

@DrawableRes
fun EnvironmentOption.getIcon(): Int {
    return when (this) {
        EnvironmentOption.Mountains -> R.drawable.ic_mountains
        EnvironmentOption.Nature -> R.drawable.ic_nature
        EnvironmentOption.Coastal -> R.drawable.ic_coastal
        EnvironmentOption.Urban -> R.drawable.ic_urban
    }
}

@DrawableRes
fun DistanceOption.getIcon(): Int {
    return R.drawable.ic_distance
}

fun DistanceOption.getString(): String {
    return when (this) {
        DistanceOption._100 -> "100km"
        DistanceOption._250 -> "250km"
        DistanceOption._500 -> "500km"
        DistanceOption.Any -> this.name
    }
}