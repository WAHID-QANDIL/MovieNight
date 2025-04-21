package org.wahid.movienight.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle

inline fun <reified T> Bundle.parcelable(key: String) =
    if (SDK_INT < TIRAMISU) {
        @Suppress("DEPRECATION")
        getParcelable(key)
    } else {
        getParcelable(key, T::class.java)
    }