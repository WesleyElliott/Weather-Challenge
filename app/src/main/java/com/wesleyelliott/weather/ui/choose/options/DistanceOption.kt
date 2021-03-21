package com.wesleyelliott.weather.ui.choose.options

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.wesleyelliott.weather.data.DistanceOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.data.getString
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.common.GridLayout
import com.wesleyelliott.weather.ui.common.TextButton
import com.wesleyelliott.weather.ui.common.items

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
                title = distance.getString(),
                icon = distance.getIcon()
            )
        }
    }
}