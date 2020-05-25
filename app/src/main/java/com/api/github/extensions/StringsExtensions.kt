package com.api.github.extensions

import com.api.github.utils.PATTERN_DATE
import com.api.github.utils.PATTERN_DEFAULT
import java.text.SimpleDateFormat
import java.util.*

internal fun String.formatToDateAndHour(pattern: String = PATTERN_DEFAULT) = try {
    val locale = Locale("pt", "BR")
    val date = SimpleDateFormat(pattern, locale).parse(this)
    SimpleDateFormat(PATTERN_DATE, locale).format(date)
} catch (e: Exception) {
    this
}