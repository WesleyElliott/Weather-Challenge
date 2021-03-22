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
        val distance =  mockDistances[weatherChoice.distanceOption]?.random() ?: 50
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