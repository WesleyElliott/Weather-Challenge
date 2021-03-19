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
import com.wesleyelliott.weather.data.*
import com.wesleyelliott.weather.ui.common.BoxState

@Composable
private fun SelectChoiceWrapper(
    boxState: BoxState,
    color: Color,
    content: @Composable (Boolean) -> Unit
) {
    val backgroundColor = animateColorAsState(
        if (boxState == BoxState.Expanded) Color.Transparent else color,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    Crossfade(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value),
        targetState = boxState == BoxState.Expanded
    ) { expanded ->
        content(expanded)
    }
}

@Composable
private fun SelectChoiceHeading(
    title: String,
    @DrawableRes icon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = MaterialTheme.typography.h4
        )
        Image(
            modifier = Modifier
                .size(180.dp)
                .padding(vertical = 12.dp)
                .alpha(0.6f)
                .offset(x = 70.dp),
            painter = painterResource(id = icon),
            contentDescription = title
        )
    }
}

@Composable
fun SelectWeatherChoice(
    boxState: BoxState,
    selectedWeather: WeatherOption? = null,
    onChoiceSelect: (WeatherOption) -> Unit
) {

    val color = Color.Blue.copy(alpha = 0.3f)
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
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
                            color = color,
                            onClick = {
                                onChoiceSelect(WeatherOption.Sunny)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_rain,
                            contentDescription = "Rainy",
                            color = color,
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
                            color = color,
                            onClick = {
                                onChoiceSelect(WeatherOption.Snowy)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_storm,
                            contentDescription = "Stormy",
                            color = color,
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
                            color = color,
                            onClick = {
                                onChoiceSelect(WeatherOption.Windy)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_rainbow,
                            contentDescription = "Calm-y",
                            color = color,
                            onClick = {
                                onChoiceSelect(WeatherOption.Calm)
                            }
                        )
                    }
                }
            }

        } else {
            val weather = selectedWeather ?: WeatherOption.Sunny
            SelectChoiceHeading(
                title = weather.name,
                icon = weather.getIcon()
            )
        }
    }
}

@Composable
fun SelectTemperatureChoice(
    boxState: BoxState,
    selectedTemperatureOption: TemperatureOption? = null,
    onChoiceSelect: (TemperatureOption) -> Unit
) {
    val color = Color.Green.copy(alpha = 0.3f)
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
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
                        text = "What temperature?",
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
                            resourceId = R.drawable.ic_hot,
                            contentDescription = "Sunny",
                            color = color,
                            onClick = {
                                onChoiceSelect(TemperatureOption.Warm)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_cold,
                            contentDescription = "Rainy",
                            color = color,
                            onClick = {
                                onChoiceSelect(TemperatureOption.Cold)
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_any_temperature,
                            contentDescription = "Any",
                            color = color,
                            onClick = {
                                onChoiceSelect(TemperatureOption.Any)
                            }
                        )
                    }
                }
            }
        } else {
            val temperature = selectedTemperatureOption ?: TemperatureOption.Any
            SelectChoiceHeading(
                title = temperature.name,
                icon = temperature.getIcon()
            )
        }
    }
}

@Composable
fun SelectEnvironmentChoice(
    boxState: BoxState,
    selectedEnvironmentOption: EnvironmentOption? = null,
    onChoiceSelect: (EnvironmentOption) -> Unit
) {
    val color = Color.Yellow.copy(alpha = 0.3f)
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
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
                        text = "What environment?",
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
                            resourceId = R.drawable.ic_mountains,
                            contentDescription = "Mountains",
                            color = color,
                            onClick = {
                                onChoiceSelect(EnvironmentOption.Mountains)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_nature,
                            contentDescription = "Nature",
                            color = color,
                            onClick = {
                                onChoiceSelect(EnvironmentOption.Nature)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_coastal,
                            contentDescription = "Coastal",
                            color = color,
                            onClick = {
                                onChoiceSelect(EnvironmentOption.Coastal)
                            }
                        )
                        SelectableImage(
                            resourceId = R.drawable.ic_urban,
                            contentDescription = "Urban",
                            color = color,
                            onClick = {
                                onChoiceSelect(EnvironmentOption.Urban)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableImage(
                            resourceId = R.drawable.ic_coastal,
                            contentDescription = "Any",
                            color = color,
                            onClick = {
                                onChoiceSelect(EnvironmentOption.Coastal)
                            }
                        )
                    }
                }
            }
        } else {
            val environment = selectedEnvironmentOption ?: EnvironmentOption.Urban
            SelectChoiceHeading(
                title = environment.name,
                icon = environment.getIcon()
            )
        }
    }
}

@Composable
fun SelectDistanceChoice(
    boxState: BoxState,
    selectedDistanceOption: DistanceOption? = null,
    onChoiceSelect: (DistanceOption) -> Unit
) {
    val color = Color.Red.copy(alpha = 0.3f)
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
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
                        text = "What distance?",
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
                        SelectableText(
                            contentDescription = "100km",
                            color = color,
                            onClick = {
                                onChoiceSelect(DistanceOption._100)
                            }
                        )
                        SelectableText(
                            contentDescription = "250km",
                            color = color,
                            onClick = {
                                onChoiceSelect(DistanceOption._250)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SelectableText(
                            contentDescription = "500km",
                            color = color,
                            onClick = {
                                onChoiceSelect(DistanceOption._500)
                            }
                        )
                        SelectableText(
                            contentDescription = "Any",
                            color = color,
                            onClick = {
                                onChoiceSelect(DistanceOption.Any)
                            }
                        )
                    }
                }
            }
        } else {
            val distance = selectedDistanceOption ?: DistanceOption.Any
            SelectChoiceHeading(
                title = distance.getString(),
                icon = distance.getIcon()
            )
        }
    }
}

@Composable
fun SelectableImage(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(130.dp)
            .background(color, MaterialTheme.shapes.medium)
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

@Composable
fun SelectableText(
    contentDescription: String,
    color: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(width = 130.dp, height = 90.dp)
            .background(color, MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = contentDescription,
            style = MaterialTheme.typography.h6
        )
    }
}