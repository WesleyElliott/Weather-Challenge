package com.wesleyelliott.weather.ui.weather

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.data.WeatherReport
import com.wesleyelliott.weather.data.WeatherRepository
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.utils.LocalUnitProvider
import com.wesleyelliott.weather.utils.MeasurementUnit

private const val backgroundShapeOffset = 20f
private val backgroundShape = GenericShape { size, _ ->
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

@OptIn(ExperimentalAnimationApi::class)
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

        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(end = 8.dp)
                .align(Alignment.TopEnd)
        ) {

            if (settingsVisible.value) {
                Popup(
                    alignment = Alignment.TopEnd,
                    onDismissRequest = {
                       settingsVisible.value = false
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
                                    unit.value = MeasurementUnit.METRIC
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Metric",
                                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp)
                            )

                            if (unit.value == MeasurementUnit.UK || unit.value == MeasurementUnit.METRIC) {
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
                                    unit.value = MeasurementUnit.IMPERIAL
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Imperial",
                                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                            )
                            if (unit.value == MeasurementUnit.IMPERIAL) {
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
                        settingsVisible.value = true
                    }
                    .padding(12.dp),
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colors.onBackground,
            )
        }

        Box(
            modifier = Modifier
                .padding(top = maxHeight / 3)
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .clip(backgroundShape)
                .background(MaterialTheme.colors.background)
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

                Column {
                    Text(
                        modifier = Modifier.padding(end = 48.dp),
                        text = weatherReport.location.name,
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Light),
                    )
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = weatherReport.location.country,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Icon(
                            imageVector = Icons.Default.PinDrop,
                            contentDescription = "Map icon"
                        )
                        Text(
                            text = unit.value.formatDistance(weatherReport),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = weatherReport.currentTemp.formatTemperature(unit.value),
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
                                    id = weatherReport.currentConditions.getIcon()
                                ),
                                contentDescription = weatherReport.currentConditions.name
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = weatherReport.currentConditions.name,
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weatherReport.forecast.forEach { forecast ->
                        ForecastBox(
                            time = forecast.time,
                            temperature = forecast.temperature.formatTemperature(unit.value),
                            icon = forecast.conditions.getIcon()
                        )
                    }
                }
            }
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

private fun Int.convertTemp(unit: MeasurementUnit): Int {
    if (unit == MeasurementUnit.IMPERIAL) {
        return (this * (9f/5f) + 32).toInt()
    }

    // For metric and UK, just return the value - its in metric already
    return this
}

private fun MeasurementUnit.formatDistance(weatherReport: WeatherReport): String {
    val unitSuffix = when (this) {
        MeasurementUnit.METRIC -> "km away"
        MeasurementUnit.IMPERIAL, MeasurementUnit.UK -> "miles away"
    }
    return "${weatherReport.distance} $unitSuffix"
}

private fun Int.formatTemperature(unit: MeasurementUnit): String {
    val convertedTemp = this.convertTemp(unit)
    return if (unit == MeasurementUnit.METRIC || unit == MeasurementUnit.UK) {
        "$convertedTemp°C"
    } else {
        "$convertedTemp°F"
    }
}