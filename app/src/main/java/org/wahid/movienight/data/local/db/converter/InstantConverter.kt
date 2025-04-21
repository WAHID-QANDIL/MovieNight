package org.wahid.movienight.data.local.db.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {
    @TypeConverter
    fun stringToInstant(value: String?) = value?.let {
        Instant.parse(it)
    }

    @TypeConverter
    fun instantToString(value: Instant?) = value?.toString()
}