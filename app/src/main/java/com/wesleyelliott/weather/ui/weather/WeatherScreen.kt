package com.wesleyelliott.weather.ui.weather

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.insets.statusBarsPadding
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.*
import com.wesleyelliott.weather.ui.utils.isVertical
import com.wesleyelliott.weather.utils.LocalUnitProvider
import com.wesleyelliott.weather.utils.MeasurementUnit

private const val backgroundShapeOffset = 20f

/**
 * Custom shape for the background of the weather screen in portrait mode
 */
private val backgroundShapePortrait = GenericShape { size, _ ->
    moveTo(0f, backgroundShapeOffset)
    cubicTo(
        x1 = size.width * 0.2f,
        y1 = -backgroundShapeOffset,
        x2 = size.width * 0.75f,
        y2 = backgroundShapeOffset,
        x3 = size.width,
        y3 = size.height * 0.33f,
    )
    lineTo(
        size.width,
        size.height
    )
    lineTo(
        0f,
        size.height
    )
    close()
}

/**
 * Custom shape for the background of the weather screen in landscape mode
 */
private val backgroundShapeLandscape = GenericShape { size, _ ->
    moveTo(size.width * 0.7f, 0f)

    cubicTo(
        x1 = size.width * 0.7f,
        y1 = size.height * 0.33f,
        x2 = size.width,
        y2 = size.height * 0.66f,
        x3 = size.width,
        y3 = size.height,
    )
    lineTo(
        0f,
        size.height
    )
    lineTo(
        0f,
        0f
    )
    close()
}

@Composable
fun WeatherScreen(
    weatherChoice: WeatherChoice
) {
    val weatherRepository = WeatherRepository()
    val settingsVisible = remember {
        mutableStateOf(false)
    }
    val unit = LocalUnitProvider.current

    val weatherReport = remember {
        weatherRepository.loadWeather(weatherChoice)
    }

    if (isVertical) {
        WeatherScreenPortrait(
            weatherReport = weatherReport,
            settingsVisible = settingsVisible.value,
            unit = unit.value,
            onSettingsChange = {
                settingsVisible.value = it
            },
            onMeasurementChange = {
                unit.value = it
            }
        )
    } else {
        WeatherScreenLandscape(
            weatherReport = weatherReport,
            settingsVisible = settingsVisible.value,
            unit = unit.value,
            onSettingsChange = {
                settingsVisible.value = it
            },
            onMeasurementChange = {
                unit.value = it
            }
        )
    }
}

/**
 * Portrait implementation of the weather report screen
 */
@Composable
fun WeatherScreenPortrait(
    weatherReport: WeatherReport,
    settingsVisible: Boolean,
    unit: MeasurementUnit,
    onSettingsChange: (Boolean) -> Unit,
    onMeasurementChange: (MeasurementUnit) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .align(Alignment.TopCenter)
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                data = weatherReport.location.imageUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = weatherReport.location.name,
                fadeIn = true,
            )
        }

        SettingsButton(
            settingsVisible = settingsVisible,
            unit = unit,
            onSettingsChange = onSettingsChange,
            onMeasurementChange = onMeasurementChange
        )

        Box(
            modifier = Modifier
                .padding(top = maxHeight / 3)
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .clip(backgroundShapePortrait)
                .background(MaterialTheme.colors.background)
        ) {
            WeatherReport(
                weatherReport = weatherReport,
                unit = unit
            )
        }
    }
}

/**
 * Landscape implementation of the weather report screen
 */
@Composable
fun WeatherScreenLandscape(
    weatherReport: WeatherReport,
    settingsVisible: Boolean,
    unit: MeasurementUnit,
    onSettingsChange: (Boolean) -> Unit,
    onMeasurementChange: (MeasurementUnit) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.60f)
                .align(Alignment.CenterEnd)
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                data = weatherReport.location.imageUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = weatherReport.location.name,
                fadeIn = true,
            )
        }

        SettingsButton(
            settingsVisible = settingsVisible,
            unit = unit,
            onSettingsChange = onSettingsChange,
            onMeasurementChange = onMeasurementChange
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.6f)
                .align(Alignment.TopStart)
                .clip(backgroundShapeLandscape)
                .background(MaterialTheme.colors.background)
        ) {

            WeatherReport(
                weatherReport = weatherReport,
                unit = unit
            )
        }
    }
}

/**
 * The actual data content of the screen to display the current conditions and forecasted
 * conditions, as well as the location
 */
@Composable
private fun WeatherReport(
    weatherReport: WeatherReport,
    unit: MeasurementUnit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                top = 40.dp,
                bottom = 30.dp,
                start = 20.dp,
                end = 20.dp
            ),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Location(
            locationName = weatherReport.location.name,
            country = weatherReport.location.country,
            distance = weatherReport.distance,
            unit = unit
        )

        CurrentConditions(
            currentTemperature = weatherReport.currentTemp,
            currentConditions = weatherReport.currentConditions,
            unit = unit
        )

        Forecast(
            forecast = weatherReport.forecast,
            unit = unit
        )
    }
}

@Composable
private fun Location(
    locationName: String,
    country: String,
    distance: Int,
    unit: MeasurementUnit
) {
    Column {
        Text(
            modifier = Modifier.padding(end = 48.dp),
            text = locationName,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Light),
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = country,
                style = MaterialTheme.typography.subtitle1
            )
            Icon(
                imageVector = Icons.Default.PinDrop,
                contentDescription = "Map icon"
            )
            Text(
                text = unit.formatDistance(distance),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
private fun CurrentConditions(
    currentTemperature: Int,
    currentConditions: WeatherOption,
    unit: MeasurementUnit,
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = unit.formatTemperature(currentTemperature),
            style = MaterialTheme.typography.h1
        )

        Column(
            modifier = Modifier
                .padding(start = 24.dp)
                .alignBy(LastBaseline)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(
                        id = currentConditions.getIcon()
                    ),
                    contentDescription = currentConditions.name
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = currentConditions.name,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_nature),
                    contentDescription = "High pollen"
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "Hgh pollen",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
private fun Forecast(
    forecast: List<Forecast>,
    unit: MeasurementUnit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        forecast.forEach { forecast ->
            ForecastBox(
                time = forecast.time,
                temperature = unit.formatTemperature(forecast.temperature),
                icon = forecast.conditions.getIcon()
            )
        }
    }
}

@Composable
private fun ForecastBox(
    time: String,
    temperature: String,
    @DrawableRes icon: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.subtitle1,
        )
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = temperature
        )
        Text(
            text = temperature,
            style = MaterialTheme.typography.subtitle1,
        )
    }
}

@Composable
private fun BoxScope.SettingsButton(
    settingsVisible: Boolean,
    unit: MeasurementUnit,
    onSettingsChange: (Boolean) -> Unit,
    onMeasurementChange: (MeasurementUnit) -> Unit,
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(end = 8.dp)
            .align(Alignment.TopEnd)
    ) {

        if (settingsVisible) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = {
                    onSettingsChange(false)
                },
                offset = IntOffset(
                    x = 0,
                    y = 140
                )
            ) {
                Column(
                    modifier = Modifier
                        .width(160.dp)
                        .background(
                            MaterialTheme.colors.background,
                            MaterialTheme.shapes.medium
                        )
                        .padding(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                onMeasurementChange(MeasurementUnit.METRIC)
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Metric",
                            style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
                        )

                        if (unit == MeasurementUnit.UK || unit == MeasurementUnit.METRIC) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Metric Setting"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickable {
                                onMeasurementChange(MeasurementUnit.IMPERIAL)
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Imperial",
                            style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                        )
                        if (unit == MeasurementUnit.IMPERIAL) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Imperial Setting"
                            )
                        }
                    }
                }
            }
        }



        Icon(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.background)
                .clickable {
                    onSettingsChange(true)
                }
                .padding(12.dp),
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colors.onBackground,
        )
    }
}

/**
 * Convert Celsius based temperature to Fahrenheit if the unit is [MeasurementUnit.IMPERIAL].
 * For [MeasurementUnit.UK] and [MeasurementUnit.METRIC] - the same value is returned
 */
private fun MeasurementUnit.convertTemp(temperature: Int): Int {
    if (this == MeasurementUnit.IMPERIAL) {
        return (temperature * (9f/5f) + 32).toInt()
    }
    return temperature
}

/**
 * Format the distance value to a readable string based on the [MeasurementUnit].
 * For [MeasurementUnit.UK] and [MeasurementUnit.IMPERIAL] miles is used, otherwise kilometers is
 * used.
 */
private fun MeasurementUnit.formatDistance(distance: Int): String {
    val unitSuffix = when (this) {
        MeasurementUnit.METRIC -> "km away"
        MeasurementUnit.IMPERIAL, MeasurementUnit.UK -> "mi away"
    }
    return "$distance $unitSuffix"
}

/**
 * Format the temperature to a readable string based on the [MeasurementUnit].
 * For [MeasurementUnit.IMPERIAL], Fahrenheit is used for the symbol, otherwise Celsius is used.
 */
private fun MeasurementUnit.formatTemperature(temperature: Int): String {
    val convertedTemp = this.convertTemp(temperature)
    return if (this == MeasurementUnit.METRIC || this == MeasurementUnit.UK) {
        "$convertedTemp°C"
    } else {
        "$convertedTemp°F"
    }
}