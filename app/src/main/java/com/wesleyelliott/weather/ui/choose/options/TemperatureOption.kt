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
import com.wesleyelliott.weather.data.TemperatureOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.common.GridLayout
import com.wesleyelliott.weather.ui.common.ImageButton
import com.wesleyelliott.weather.ui.common.items
import com.wesleyelliott.weather.ui.utils.isVertical

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

/**
 * Selection choice for selecting the temperature options.
 */
@Composable
fun SelectTemperatureChoice(
    boxState: BoxState,
    selectedTemperatureOption: TemperatureOption? = null,
    onChoiceSelect: (TemperatureOption) -> Unit
) {
    val color = MaterialTheme.colors.background
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    modifier = if (!isVertical) Modifier.statusBarsPadding() else Modifier,
                    title = "What temperature?"
                )

                GridLayout(
                    columnCount = if (isVertical) 2 else 3
                ) {
                    items(temperatureChoices) { item ->
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
            val temperature = selectedTemperatureOption ?: TemperatureOption.Any
            CollapsedChoiceHeading(
                modifier = if (!isVertical) Modifier.statusBarsPadding() else Modifier,
                title = temperature.name,
                icon = temperature.getIcon()
            )
        }
    }
}
