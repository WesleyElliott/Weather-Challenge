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
package com.wesleyelliott.weather

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.wesleyelliott.weather.data.WeatherChoice
import com.wesleyelliott.weather.nav.Nav
import com.wesleyelliott.weather.ui.choose.ChooseScreen
import com.wesleyelliott.weather.ui.common.rememberAccordionState
import com.wesleyelliott.weather.ui.theme.MyTheme
import com.wesleyelliott.weather.ui.weather.WeatherScreen
import com.wesleyelliott.weather.utils.LocalUnitProvider
import com.wesleyelliott.weather.utils.MeasurementUnit
import com.wesleyelliott.weather.utils.getLocaleUnits
import java.util.Locale

/**
 * Allow the [WeatherApp] to dispatch the system [onBackPressed] to the Activity
 */
interface BackDispatcher {
    fun onBackPressed()
}

class MainActivity : AppCompatActivity(), BackDispatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val defaultUnitSystem = rememberSaveable(
                saver = Saver(
                    save = {
                        Bundle().apply {
                            putSerializable("unit", it.value)
                        }
                    },
                    restore = {
                        val unit = it.getSerializable("unit") as? MeasurementUnit ?: Locale.getDefault().getLocaleUnits()
                        mutableStateOf(unit)
                    }
                )
            ) {
                mutableStateOf(Locale.getDefault().getLocaleUnits())
            }
            ProvideWindowInsets {
                CompositionLocalProvider(
                    LocalUnitProvider provides defaultUnitSystem
                ) {
                    MyTheme(darkTheme = true) {
                        WeatherApp(this)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherApp(
    backDispatcher: BackDispatcher
) {
    /**
     * Keep track of the navigation destination state
     *
     * Using this instead of the Nav Component as I want some transition support when navigating
     * between destinations.
     * Saveable as we need to save and restore the state across config changes
     */
    val navState = rememberSaveable(saver = Nav.Saver) {
        mutableStateOf(Nav.Choose)
    }
    val accordionLayoutState = rememberAccordionState()

    val selectedWeather = rememberSaveable {
        mutableStateOf<WeatherChoice?>(null)
    }

    /**
     * Consume the back button to allow going back from the [WeatherScreen] to the [ChooseScreen].
     * If we're on the [ChooseScreen], go back in the accordion until the first item is expanded.
     * Finally, dispatch a system back event if the first item is expanded.
     */
    LocalOnBackPressedDispatcherOwner.current.onBackPressedDispatcher.addCallback {
        val currentNav = navState.value
        when {
            currentNav is Nav.Weather -> {
                // When we're on the weather screen, go back to the choice screen
                navState.value = Nav.Choose
            }
            accordionLayoutState.currentItem.value > 0 -> {
                // When we're on the choice screen, and the accordion is not in the
                // initial state, go back in the accordion
                accordionLayoutState.goBack()
            }
            else -> {
                // Otherwise, use system back
                remove()
                backDispatcher.onBackPressed()
            }
        }
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        AnimatedVisibility(
            visible = navState.value == Nav.Choose,
            enter = slideInHorizontally(
                initialOffsetX = { -it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it }
            )
        ) {
            ChooseScreen(
                accordionLayoutState = accordionLayoutState
            ) {
                navState.value = Nav.Weather(it)
                selectedWeather.value = it
            }
        }

        AnimatedVisibility(
            visible = navState.value is Nav.Weather,
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it }
            )
        ) {
            val current = selectedWeather.value
            if (current != null) {
                WeatherScreen(weatherChoice = current)
            }
        }
    }
}

private val mockBackDispatcher = object : BackDispatcher {
    override fun onBackPressed() {
        //
    }
}
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        WeatherApp(mockBackDispatcher)
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        WeatherApp(mockBackDispatcher)
    }
}
