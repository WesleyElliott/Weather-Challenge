package com.wesleyelliott.weather.utils

import android.icu.util.LocaleData
import android.icu.util.ULocale
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.*

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