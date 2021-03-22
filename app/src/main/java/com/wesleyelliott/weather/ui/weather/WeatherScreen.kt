package com.wesleyelliott.weather.ui.weather

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.CoilImage
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.*
import com.wesleyelliott.weather.utils.MeasurementUnit
import com.wesleyelliott.weather.utils.getLocaleUnits
import java.util.*

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

@Composable
fun WeatherScreen(
    weatherChoice: WeatherChoice
) {
    val unit = Locale.getDefault().getLocaleUnits()
    val weatherReport = WeatherRepository().loadWeather(weatherChoice, unit)

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
                        start = 40.dp,
                        end = 40.dp
                    ),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Column {
                    Text(weatherReport.location.name, style = MaterialTheme.typography.h2)
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.PinDrop,
                            contentDescription = "Map icon"
                        )
                        Text(
                            text = formatDistance(weatherReport),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = weatherReport.currentTemp.formatTemperature(unit),
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
                            temperature = forecast.temperature.formatTemperature(unit),
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

private fun formatDistance(weatherReport: WeatherReport): String {
    val unitSuffix = when (weatherReport.distanceUnit) {
        DistanceUnit.Km -> "km away"
        DistanceUnit.Mi -> "miles away"
    }
    return "${weatherReport.distance} $unitSuffix"
}

private fun Int.formatTemperature(unit: MeasurementUnit): String {
    return if (unit == MeasurementUnit.METRIC || unit == MeasurementUnit.UK) {
        "$this°C"
    } else {
        "$this°F"
    }
}