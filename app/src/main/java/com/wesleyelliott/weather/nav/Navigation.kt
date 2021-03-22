package com.wesleyelliott.weather.nav

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.data.fromBundle
import com.wesleyelliott.weather.data.toBundle

private const val WEATHER_CHOICE_KEY = "weather_choice"

/**
 * Sealed class to represent the navigation destinations
 */
sealed class Nav {
    object Choose : Nav()
    data class Weather(val weatherChoice: WeatherChoice) : Nav()

    companion object {
        /**
         * Custom saver to save and restore nav state across config changes
         */
        val Saver: Saver<MutableState<Nav>, *> = Saver(
            save = {
                when (val state = it.value) {
                    // Save an empty bundle for [Nav.Choose] as its the default
                    Choose -> Bundle()
                    is Weather -> Bundle().apply {
                        putBundle(WEATHER_CHOICE_KEY, state.weatherChoice.toBundle())
                    }
                }
            },
            restore = {
                // If we don't have a weather choice, default to [Nav.Choose]
                val weatherChoice = it.getBundle(WEATHER_CHOICE_KEY)?.fromBundle()
                if (weatherChoice != null) {
                    mutableStateOf(Weather(weatherChoice))
                } else {
                    mutableStateOf(Choose)
                }
            }
        )
    }
}
