package com.wesleyelliott.weather.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.wesleyelliott.weather.R
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class WeatherChoice(
    val weatherOption: WeatherOption? = null,
    val temperatureOption: TemperatureOption? = null,
    val environmentOption: EnvironmentOption? = null,
    val distanceOption: DistanceOption? = null,
) : Parcelable

@Parcelize
data class WeatherReport(
    val location: Location,
    val distance: Int,
    val currentTemp: Int,
    val currentConditions: WeatherOption,
    val forecast: List<Forecast> = emptyList()
) : Parcelable

@Parcelize
data class Forecast(
    val time: String,
    val conditions: WeatherOption,
    val temperature: Int
) : Parcelable

@Parcelize
data class Location(
    val name: String,
    val country: String,
    val imageUrl: String
) : Parcelable

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