package com.wesleyelliott.weather.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * A button that contains an icon with text below it
 *
 * @param modifier the modifier to apply to the button
 * @param text the text to display on the button
 * @param iconRes the image icon resource to show on the button
 * @param color color to be applied to the button
 * @param onClick the click action handler for the button
 */
@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .size(120.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.secondary)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = iconRes),
            contentDescription = text
        )
        Text(
            text = text,
            style = MaterialTheme.typography.h6
        )
    }
}

/**
 * A button that contains only text
 *
 * @param modifier the modifier to apply to the button
 * @param text the text to display on the button
 * @param color color to be applied to the button
 * @param onClick the click action handler for the button
 */
@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .size(width = 120.dp, height = 90.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.secondary)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6
        )
    }
}