package com.wesleyelliott.weather.ui.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Returns if the current orientation of the screen is potrait or not
 */
val isVertical: Boolean
    @Composable
    get() = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT