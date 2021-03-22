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
package com.wesleyelliott.weather.ui.choose.options

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
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
                    modifier = Modifier.statusBarsPadding(),
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
                modifier = Modifier.statusBarsPadding(),
                title = weather.name,
                icon = weather.getIcon()
            )
        }
    }
}
