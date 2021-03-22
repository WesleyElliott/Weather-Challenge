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
package com.wesleyelliott.weather.utils

import android.icu.util.LocaleData
import android.icu.util.ULocale
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

/**
 * This composition local allows child composables of the app to access the current measurement
 * unit used by the app. It defaults to the system settings, but can be changed during an app
 * session to the users preference.
 * This is reset on every app launch.
 */
val LocalUnitProvider = staticCompositionLocalOf {
    mutableStateOf(Locale.getDefault().getLocaleUnits())
}

/**
 * Our own representation of the different measurement units available
 */
enum class MeasurementUnit {
    METRIC,
    IMPERIAL,
    UK // They just have to be different...
}

/**
 * Helper function to get the unit system the phone uses, based on the user's selected [Locale]
 */
fun Locale.getLocaleUnits(): MeasurementUnit {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        return when (LocaleData.getMeasurementSystem(ULocale.forLocale(this))) {
            LocaleData.MeasurementSystem.SI -> {
                MeasurementUnit.METRIC
            }
            LocaleData.MeasurementSystem.UK -> {
                MeasurementUnit.UK
            }
            LocaleData.MeasurementSystem.US -> {
                MeasurementUnit.IMPERIAL
            }
            else -> {
                MeasurementUnit.METRIC
            }
        }
    } else {
        return when (this.country) {
            "UK" -> MeasurementUnit.UK
            "US", "LR", "MM" -> MeasurementUnit.IMPERIAL
            else -> MeasurementUnit.METRIC
        }
    }
}
