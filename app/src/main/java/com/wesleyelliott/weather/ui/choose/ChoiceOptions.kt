package com.wesleyelliott.weather.ui.choose

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.WeatherOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.ui.common.BoxState

@Composable
fun SelectWeatherChoice(
    boxState: BoxState,
    selectedWeather: WeatherOption? = null,
    onChoiceSelect: (WeatherOption) -> Unit
) {
    val backgroundColor = animateColorAsState(
        if (boxState == BoxState.Expanded) Color.Transparent else Color.Blue.copy(alpha = 0.3f),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    Crossfade(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value),
        targetState = boxState == BoxState.Expanded
    ) { expanded ->

        if (expanded) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "What conditions?",
                        style = MaterialTheme.typography.h4
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_sun,
                            contentDescription = "Sunny",
                            onClick = {
                                onChoiceSelect(WeatherOption.Sunny)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_rain,
                            contentDescription = "Rainy",
                            onClick = {
                                onChoiceSelect(WeatherOption.Rainy)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_snow,
                            contentDescription = "Snowy",
                            onClick = {
                                onChoiceSelect(WeatherOption.Snowy)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_storm,
                            contentDescription = "Stormy",
                            onClick = {
                                onChoiceSelect(WeatherOption.Stormy)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_wind,
                            contentDescription = "Windy",
                            onClick = {
                                onChoiceSelect(WeatherOption.Windy)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_rainbow,
                            contentDescription = "Calm-y",
                            onClick = {
                                onChoiceSelect(WeatherOption.Calm)
                            }
                        )
                    }
                }
            }

        } else {
            val weather = selectedWeather ?: WeatherOption.Sunny
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = weather.name,
                    style = MaterialTheme.typography.h4
                )
                Image(
                    modifier = Modifier
                        .size(180.dp)
                        .alpha(0.6f)
                        .offset(x = 70.dp),
                    painter = painterResource(id = weather.getIcon()),
                    contentDescription = weather.name
                )
            }
        }
    }
}

@Composable
fun SelectableImage(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(130.dp)
            .background(Color.Blue.copy(alpha = 0.3f), MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription
        )
        Text(
            text = contentDescription,
            style = MaterialTheme.typography.h6
        )
    }
}