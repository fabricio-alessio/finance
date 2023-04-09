package com.fasolutions.finance.application.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SimpleDate(
    val value: String
) {
    companion object {
        fun now(): SimpleDate {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val value = dateFormatter.format(Date())
            return SimpleDate(value)
        }
    }
}