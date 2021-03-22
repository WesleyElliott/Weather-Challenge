package com.wesleyelliott.weather.utils

import android.icu.util.LocaleData
import android.icu.util.ULocale
import android.os.Build
import java.util.*

enum class MeasurementUnit {
    METRIC,
    IMPERIAL,
    UK // They just have to be different...
}

fun Locale.getLocaleUnits(override: MeasurementUnit? = null): MeasurementUnit {
    if (override != null) {
        return override
    }
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