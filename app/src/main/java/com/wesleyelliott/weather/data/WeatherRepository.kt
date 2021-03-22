package com.wesleyelliott.weather.data

import com.wesleyelliott.weather.utils.MeasurementUnit


private val mockLocations = listOf(
    Location(
        name = "Tokyo",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "Paris",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "New York",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "London",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "Sydney",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "Cape Town",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
    Location(
        name = "Rio",
        imageUrl = "https://i.pinimg.com/originals/2e/80/4a/2e804af2fe69257bc01a54ba74d88848.jpg"
    ),
)

private val mockDistances = mapOf(
    DistanceOption._100 to (0..100),
    DistanceOption._250 to (100..250),
    DistanceOption._500 to (250..500),
    DistanceOption.Any to (0..500),
)

private val mockTemperatures = mapOf(
    TemperatureOption.Warm to (20..32),
    TemperatureOption.Cold to (-20..10),
    TemperatureOption.Any to (-20..32),
)

class WeatherRepository {

    fun loadWeather(weatherChoice: WeatherChoice, unit: MeasurementUnit): WeatherReport {
        val location = mockLocations.random()
        val distance =  mockDistances[weatherChoice.distanceOption]?.random() ?: 50
        val currentTemperature = mockTemperatures[weatherChoice.temperatureOption]?.random() ?: 15
        val weatherOption = weatherChoice.weatherOption!!
        val forecast = listOf(
            Forecast(
                "19:00", weatherOption, (currentTemperature - 1).convertTemp(unit)
            ),
            Forecast(
                "21:00", weatherOption, (currentTemperature - 2).convertTemp(unit)
            ),
            Forecast(
                "23:00", weatherOption, (currentTemperature - 4).convertTemp(unit)
            ),
            Forecast(
                "01:00", weatherOption, (currentTemperature - 5).convertTemp(unit)
            ),
        )

        return WeatherReport(
            location = location,
            distance = distance,
            distanceUnit = if (unit == MeasurementUnit.METRIC) DistanceUnit.Km else DistanceUnit.Mi,
            currentTemp = currentTemperature.convertTemp(unit),
            currentConditions = weatherOption,
            forecast = forecast
        )
    }

    private fun Int.convertTemp(unit: MeasurementUnit): Int {
        if (unit == MeasurementUnit.IMPERIAL) {
            return (this * (9f/5f) + 32).toInt()
        }

        // For metric and UK, just return the value - its in metric already
        return this
    }
}