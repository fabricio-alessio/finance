package com.fasolutions.finance.application.service

import com.fasolutions.finance.application.model.CompanyReport

interface ReportPresenter {
    fun draw(companyReports: List<CompanyReport>): String
}