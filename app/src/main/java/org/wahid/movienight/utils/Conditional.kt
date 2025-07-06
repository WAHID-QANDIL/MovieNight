package org.wahid.movienight.utils

import androidx.compose.ui.Modifier

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier {
    return if (condition) ifTrue(this) else ifFalse(this)
}