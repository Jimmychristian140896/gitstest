package com.jimmy.basecompose.core.util

import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatUtcToLocalDateTime(): String {
    val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", Locale.getDefault())

    //val zonedDateTime = inputFormatter.parse(this)
    val zonedDateTime = ZonedDateTime.parse(this, inputFormatter)
        .withZoneSameInstant(ZoneId.systemDefault())
    return outputFormatter.format(zonedDateTime)
}

fun String.getFirstSentence(): String {
    val sentenceEnd = indexOf('.')
    return if (sentenceEnd != -1) {
        substring(0, sentenceEnd + 1)
    } else {
        this
    }
}

fun String.isValidEmail(): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val hasUppercase = this.any { it.isUpperCase() }
    val hasLowercase = this.any { it.isLowerCase() }
    val hasDigit = this.any { it.isDigit() }
    val hasSymbol = this.any { !it.isLetterOrDigit() }
    val minLength = this.length >= 8
    return hasUppercase && hasLowercase && hasDigit && hasSymbol && minLength
}