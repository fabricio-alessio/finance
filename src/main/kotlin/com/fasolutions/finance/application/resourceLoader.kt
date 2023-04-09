package com.fasolutions.finance.application

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

object ResourceLoader {
    fun getCompanyCodesFile() = File(getUrlFile("companies.txt").file)
    fun getAllCompanyCodesFile() = File(getUrlFile("companies-all.txt").file)
    fun getMyCompanyCodesFile() = File(getUrlFile("companies-my.txt").file)
    fun getIndicatorHistoryFile() = File(getUrlFile("indicator-history-response.json").file)
    fun getCustodyPositionFile() = File(getUrlFile("nu-invest-position.json").file)
    fun getFundamentusFile() = File(getUrlFile("fundamentus.html").file)
    fun getEvaluationsFile() = File(getUrlFile("evaluations.csv").file)
    private fun getUrlFile(fileName: String) = this.javaClass.getResource("/$fileName")!!

    fun loadFileAsLines(file: File): List<String> {
        val lines = mutableListOf<String>()
        try {
            BufferedReader(FileReader(file)).use { br ->
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    lines.add(line!!)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return lines
    }
}