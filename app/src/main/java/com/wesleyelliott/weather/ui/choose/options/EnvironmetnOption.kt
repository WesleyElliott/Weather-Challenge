package com.wesleyelliott.weather.ui.choose.options

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.wesleyelliott.weather.R
import com.wesleyelliott.weather.data.EnvironmentOption
import com.wesleyelliott.weather.data.getIcon
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.common.GridLayout
import com.wesleyelliott.weather.ui.common.ImageButton
import com.wesleyelliott.weather.ui.common.items
import com.wesleyelliott.weather.ui.utils.isVertical

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

/**
 * Selection choice for selecting the environment options.
 */
@Composable
fun SelectEnvironmentChoice(
    boxState: BoxState,
    selectedEnvironmentOption: EnvironmentOption? = null,
    onChoiceSelect: (EnvironmentOption) -> Unit
) {
    val color = MaterialTheme.colors.surface
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