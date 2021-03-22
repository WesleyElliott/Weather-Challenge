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
import com.wesleyelliott.weather.data.DistanceOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.data.getString
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.common.GridLayout
import com.wesleyelliott.weather.ui.common.TextButton
import com.wesleyelliott.weather.ui.common.items
import com.wesleyelliott.weather.ui.utils.isVertical

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
 * Selection choice for selecting the distance options.
 */
@Composable
fun SelectDistanceChoice(
    boxState: BoxState,
    selectedDistanceOption: DistanceOption? = null,
    onChoiceSelect: (DistanceOption) -> Unit
) {
    val color = MaterialTheme.colors.surface
    SelectChoiceWrapper(
        boxState = boxState,
        color = color
    ) { expanded ->
        if (expanded) {
            Column {
                ExpandedChoiceHeading(
                    modifier = if (!isVertical) Modifier.statusBarsPadding() else Modifier,
                    title = "What distance?"
                )

                GridLayout(
                    columnCount = 2
                ) {
                    items(distanceChoices) { item ->
                        TextButton(
                            text = item.text,
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
                modifier = if (!isVertical) Modifier.statusBarsPadding() else Modifier,
                title = distance.getString(),
                icon = distance.getIcon()
            )
        }
    }
}
