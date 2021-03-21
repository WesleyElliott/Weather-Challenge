package com.wesleyelliott.weather.ui.choose.options

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.WeatherOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.common.GridLayout
import com.wesleyelliott.weather.ui.common.ImageButton
import com.wesleyelliott.weather.ui.common.items
import com.wesleyelliott.weather.ui.utils.isVertical

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

/**
 * Selection choice for selecting the weather options.
 */
@Composable
fun SelectWeatherChoice(
    boxState: BoxState,
    selectedWeather: WeatherOption? = null,
    onChoiceSelect: (WeatherOption) -> Unit
) {
    val color = MaterialTheme.colors.background
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