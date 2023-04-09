package com.fasolutions.finance.integration.fundamentus.mapper

import com.fasolutions.finance.application.model.CompanyBasics

class BasicsMapper {

    companion object {
        const val SECTOR_MATCHER = "<span class=\"txt\">Setor</span>"
        const val SUB_SECTOR_MATCHER = "<span class=\"txt\">Subsetor</span>"
        const val PAPER = "<span class=\"txt\">Papel</span>"
        const val TYPE = "<span class=\"txt\">Tipo</span>"
        const val ENTERPRISE = "<span class=\"txt\">Empresa</span>"
    }

    fun map(lines: List<String>): CompanyBasics {

        var sector = ""
        var subSector = ""
        var paper = ""
        var type = ""
        var enterprise = ""
        var lineNumber = 0
        val maxLineNumber = lines.size-1
        while (lineNumber < maxLineNumber) {
            val line = lines[lineNumber]
            if (line.contains(SECTOR_MATCHER)) {
                lineNumber++
                val lineWithSector = lines[lineNumber]
                sector = getData("a href", lineWithSector)
            }
            if (line.contains(SUB_SECTOR_MATCHER)) {
                lineNumber++
                val lineWithSubSector = lines[lineNumber]
                subSector = getData("a href", lineWithSubSector)
            }
            if (line.contains(TYPE)) {
                lineNumber++
                val lineWithType = lines[lineNumber]
                type = getData("span class", lineWithType)
            }
            if (line.contains(ENTERPRISE)) {
                lineNumber++
                val lineWithEnterprise = lines[lineNumber]
                enterprise = getData("span class", lineWithEnterprise)
            }
            if (line.contains(PAPER)) {
                lineNumber++
                val lineWithSubSector = lines[lineNumber]
                paper = getData("span class", lineWithSubSector)
            }
            lineNumber++
        }

        return CompanyBasics(
            code = paper,
            sector = sector,
            subSector = subSector,
            type = type,
            name = enterprise
        )
    }

    private fun getData(field: String, line: String): String {
        val indexOfField = line.indexOf(field)
        val subLineWithData = line.subSequence(indexOfField, line.length)
        val indexOfStartData = subLineWithData.indexOf('>')+1
        val indexOfEndData = subLineWithData.indexOf('<')
        return subLineWithData.subSequence(indexOfStartData, indexOfEndData).toString()
    }
}
