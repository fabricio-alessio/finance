package com.fasolutions.finance.presenter

fun Int?.format() = this?.let { String.format("%d", it) } ?: ""
