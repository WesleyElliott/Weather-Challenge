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

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.wesleyelliott.weather.ui.common.BoxState
import com.wesleyelliott.weather.ui.utils.isVertical

private val TITLE_HEIGHT_VERTICAL = 120.dp
private val TITLE_HEIGHT_HORIZONTAL = 50.dp

data class IconOption<T>(
    val text: String,
    @DrawableRes val icon: Int,
    val option: T
)

data class TextOption<T>(
    val text: String,
    val option: T
)

/**
 * Wrapper to the choice composables that will animate the background color and the size from
 * the expanded to the collapsed state.
 */
@Composable
fun SelectChoiceWrapper(
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
fun CollapsedChoiceHeading(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int
) {
    if (isVertical) {
        Row(
            modifier = modifier.fillMaxWidth(),
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
                    .size(150.dp)
                    .padding(vertical = 12.dp)
                    .alpha(0.6f)
                    .offset(x = 50.dp),
                painter = painterResource(id = icon),
                contentDescription = title
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxHeight(),
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
fun ExpandedChoiceHeading(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
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
