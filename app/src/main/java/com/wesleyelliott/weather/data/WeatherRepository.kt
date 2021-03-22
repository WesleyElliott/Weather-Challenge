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
package com.wesleyelliott.weather.data

class WeatherRepository {

    /**
     * Load the weather report for the given [weatherChoice].
     *
     * NB: This is all fake data, using the mocked data sets in the MockData.kt file.
     * However, it is slightly smarter than pure random. Based on the selected [TemperatureOption]
     * in the [weatherChoice], a valid range of temperatures is returned. Same for [DistanceOption]
     * and the [EnvironmentOption].
     *
     * Note, all values in the mock data are in Metric. For phones using the Imperial system,
     * the values will be converted at the view layer
     */
    fun loadWeather(weatherChoice: WeatherChoice): WeatherReport {
        val location = mockLocations[weatherChoice.environmentOption]?.random() ?: Location("Unknown", "", "")
        val distance = mockDistances[weatherChoice.distanceOption]?.random() ?: 50
        val currentTemperature = mockTemperatures[weatherChoice.temperatureOption]?.random() ?: 15
        val weatherOption = weatherChoice.weatherOption!!
        val forecast = listOf(
            Forecast(
                "19:00", weatherOption, (currentTemperature - 1)
            ),
            Forecast(
                "21:00", weatherOption, (currentTemperature - 2)
            ),
            Forecast(
                "23:00", weatherOption, (currentTemperature - 4)
            ),
            Forecast(
                "01:00", weatherOption, (currentTemperature - 5)
            ),
        )

        return WeatherReport(
            location = location,
            distance = distance,
            currentTemp = currentTemperature,
            currentConditions = weatherOption,
            forecast = forecast
        )
    }
}
