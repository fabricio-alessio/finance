package com.fasolutions.finance.application.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CompanyPositionsTest {

    @Test
    fun `Should change a existent position`() {

        val companyPositions = CompanyPositions(
            code = "AGRO3",
            positions = mutableListOf(
                CompanyPositions.Position(
                    date = SimpleDate.now(),
                    currentPrice = 12.0,
                    averagePrice = 5.0,
                    totalQuantity = 20
                )
            )
        )

        companyPositions.changePositionByDate(
            CompanyPositions.Position(
                date = SimpleDate.now(),
                currentPrice = 24.0,
                averagePrice = 10.0,
                totalQuantity = 40
            )
        )

        Assertions.assertEquals(1, companyPositions.positions.size)
        val firstPosition = companyPositions.positions.first()

        Assertions.assertEquals(24.0, firstPosition.currentPrice)
        Assertions.assertEquals(10.0, firstPosition.averagePrice)
        Assertions.assertEquals(40, firstPosition.totalQuantity)
    }
}