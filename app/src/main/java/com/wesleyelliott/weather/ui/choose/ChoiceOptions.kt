package com.wesleyelliott.weather.ui.choose

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.*
import com.wesleyelliott.weather.ui.common.*
import com.wesleyelliott.weather.ui.theme.baseGreen
import com.wesleyelliott.weather.ui.theme.basePink
import com.wesleyelliott.weather.ui.theme.lightBlue
import com.wesleyelliott.weather.ui.theme.lightYellow
import com.wesleyelliott.weather.ui.utils.isVertical

private val TITLE_HEIGHT_VERTICAL = 120.dp
private val TITLE_HEIGHT_HORIZONTAL = 80.dp

data class IconOption<T>(
    val text: String,
    @DrawableRes val icon: Int,
    val option: T
)

data class TextOption<T>(
    val text: String,
    val option: T
)

private val weatherChoices = listOf(
    IconOption(
        text = "Sunny",
        icon = R.drawable.ic_sun,
        option = WeatherOption.Sunny
    ),
    IconOption(
        text = "Rainy",
        icon = R.drawable.ic_rain,
        option = WeatherOption.Rainy
    ),
    IconOption(
        text = "Stormy",
        icon = R.drawable.ic_storm,
        option = WeatherOption.Stormy
    ),
    IconOption(
        text = "Snowy",
        icon = R.drawable.ic_snow,
        option = WeatherOption.Snowy
    ),
    IconOption(
        text = "Windy",
        icon = R.drawable.ic_wind,
        option = WeatherOption.Windy
    ),
    IconOption(
        text = "Calm",
        icon = R.drawable.ic_rainbow,
        option = WeatherOption.Calm
    ),
)

private val temperatureChoices = listOf(
    IconOption(
        text = "Hot",
        icon = R.drawable.ic_hot,
        option = TemperatureOption.Warm,
    ),
    IconOption(
        text = "Cold",
        icon = R.drawable.ic_cold,
        option = TemperatureOption.Cold,
    ),
    IconOption(
        text = "Any",
        icon = R.drawable.ic_any_temperature,
        option = TemperatureOption.Any,
    ),
)

private val environmentChoices = listOf(
    IconOption(
        text = "Mountains",
        icon = R.drawable.ic_mountains,
        option = EnvironmentOption.Mountains
    ),
    IconOption(
        text = "Nature",
        icon = R.drawable.ic_nature,
        option = EnvironmentOption.Nature
    ),
    IconOption(
        text = "Coastal",
        icon = R.drawable.ic_coastal,
        option = EnvironmentOption.Coastal
    ),
    IconOption(
        text = "Urban",
        icon = R.drawable.ic_urban,
        option = EnvironmentOption.Urban
    ),
)

private val distanceChoices = listOf(
    TextOption(
        text = "100km",
        option = DistanceOption._100
    ),
    TextOption(
        text = "250km",
        option = DistanceOption._250
    ),
    TextOption(
        text = "500km",
        option = DistanceOption._500
    ),
    TextOption(
        text = "Any",
        option = DistanceOption.Any
    ),
)

/**
 * Wrapper to the choice composables that will animate the background color and the size from
 * the expanded to the collapsed state.
 */
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

/**
 * The heading for the selection choice in the collapsed state.
 */
@Composable
private fun CollapsedChoiceHeading(
    title: String,
    @DrawableRes icon: Int
) {
    if (isVertical) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    .offset(x = 50.dp),
                painter = painterResource(id = icon),
                contentDescription = title
            )
        }
    } else {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = title,
                style = MaterialTheme.typography.h4.copy(fontSize = 30.sp)
            )
            Image(
                modifier = Modifier
                    .size(180.dp)
                    .padding(horizontal = 12.dp)
                    .alpha(0.6f)
                    .offset(
                        y = 50.dp
                    ),
                painter = painterResource(id = icon),
                contentDescription = title
            )
        }
    }
}

/**
 * The heading for the selection choice in the expanded state.
 */
@Composable
private fun ExpandedChoiceHeading(
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isVertical) TITLE_HEIGHT_VERTICAL else TITLE_HEIGHT_HORIZONTAL
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = MaterialTheme.typography.h4
        )
    }
}

/**
 * Selection choice for selecting the weather options.
 */
@Composable
fun SelectWeatherChoice(
    boxState: BoxState,
    selectedWeather: WeatherOption? = null,
    onChoiceSelect: (WeatherOption) -> Unit
) {
    val color = lightBlue
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    title = "What conditions?"
                )

                GridLayout(
                    columnCount = if (isVertical) 2 else 3
                ) {
                    items(weatherChoices) { item ->
                        ImageButton(
                            text = item.text,
                            iconRes = item.icon,
                            color = color,
                            onClick = {
                                onChoiceSelect(item.option)
                            }
                        )
                    }
                }
            }

        } else {
            val weather = selectedWeather ?: WeatherOption.Sunny
            CollapsedChoiceHeading(
                title = weather.name,
                icon = weather.getIcon()
            )
        }
    }
}

/**
 * Selection choice for selecting the temperature options.
 */
@Composable
fun SelectTemperatureChoice(
    boxState: BoxState,
    selectedTemperatureOption: TemperatureOption? = null,
    onChoiceSelect: (TemperatureOption) -> Unit
) {
    val color = lightYellow
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    title = "What temperature?"
                )

                GridLayout(
                    columnCount = if (isVertical) 2 else 3
                ) {
                    items(temperatureChoices) { item ->
                        ImageButton(
                            text = item.text,
                            iconRes = item.icon,
                            color = color,
                            onClick = {
                                onChoiceSelect(item.option)
                            }
                        )
                    }
                }
            }
        } else {
            val temperature = selectedTemperatureOption ?: TemperatureOption.Any
            CollapsedChoiceHeading(
                title = temperature.name,
                icon = temperature.getIcon()
            )
        }
    }
}

/**
 * Selection choice for selecting the environment options.
 */
@Composable
fun SelectEnvironmentChoice(
    boxState: BoxState,
    selectedEnvironmentOption: EnvironmentOption? = null,
    onChoiceSelect: (EnvironmentOption) -> Unit
) {
    val color = baseGreen
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    title = "What environment?"
                )

                GridLayout(
                    columnCount = if (isVertical) 2 else 3
                ) {
                    items(environmentChoices) { item ->
                        ImageButton(
                            text = item.text,
                            iconRes = item.icon,
                            color = color,
                            onClick = {
                                onChoiceSelect(item.option)
                            }
                        )
                    }
                }
            }
        } else {
            val environment = selectedEnvironmentOption ?: EnvironmentOption.Urban
            CollapsedChoiceHeading(
                title = environment.name,
                icon = environment.getIcon()
            )
        }
    }
}

/**
 * Selection choice for selecting the distance options.
 */
@Composable
fun SelectDistanceChoice(
    boxState: BoxState,
    selectedDistanceOption: DistanceOption? = null,
    onChoiceSelect: (DistanceOption) -> Unit
) {
    val color = basePink
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    title = "What distance?"
                )

                GridLayout(
                    columnCount = 2
                ) {
                    items(distanceChoices) { item ->
                        TextButton(
                            text = item.text,
                            color = color,
                            onClick = {
                                onChoiceSelect(item.option)
                            }
                        )
                    }
                }
            }
        } else {
            val distance = selectedDistanceOption ?: DistanceOption.Any
            CollapsedChoiceHeading(
                title = distance.getString(),
                icon = distance.getIcon()
            )
        }
    }
}