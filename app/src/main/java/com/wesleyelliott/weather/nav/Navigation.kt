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
package com.wesleyelliott.weather.nav

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import com.wesleyelliott.weather.data.WeatherChoice

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
                        putParcelable(WEATHER_CHOICE_KEY, state.weatherChoice)
                    }
                }
            },
            restore = {
                // If we don't have a weather choice, default to [Nav.Choose]
                val weatherChoice = it.getParcelable<WeatherChoice>(WEATHER_CHOICE_KEY)
                if (weatherChoice != null) {
                    mutableStateOf(Weather(weatherChoice))
                } else {
                    mutableStateOf(Choose)
                }
            }
        )
    }
}
