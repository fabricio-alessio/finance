package com.fasolutions.finance.presenter

fun Double?.format() = this?.let { String.format("%.${2}f", it) } ?: ""
