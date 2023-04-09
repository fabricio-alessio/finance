package com.fasolutions.finance.application.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CompanyProventsTest {

    @Test
    fun `Should list provents of last 5 years filling with zeros when provents has less than 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2022 to 2.0,
                2021 to 1.0,
                2020 to 1.5
            )
        )

        val lastProvents = provents.lastYears(5)
        Assertions.assertEquals(5, lastProvents.size)
        Assertions.assertEquals(0.0, lastProvents[0])
        Assertions.assertEquals(1.5, lastProvents[2])
        Assertions.assertEquals(1.0, lastProvents[3])
        Assertions.assertEquals(2.0, lastProvents[4])
    }

    @Test
    fun `Should list provents of last 5 years when provents has more than 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2019 to 5.0,
                2018 to 4.0,
                2017 to 3.0,
                2022 to 2.0,
                2021 to 1.0,
                2020 to 1.5
            )
        )

        val lastProvents = provents.lastYears(5)
        Assertions.assertEquals(5, lastProvents.size)
        Assertions.assertEquals(4.0, lastProvents[0])
        Assertions.assertEquals(5.0, lastProvents[1])
        Assertions.assertEquals(1.5, lastProvents[2])
        Assertions.assertEquals(1.0, lastProvents[3])
        Assertions.assertEquals(2.0, lastProvents[4])
    }

    @Test
    fun `Should list provents of last 2 years when provents has more than 2 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2019 to 5.0,
                2018 to 4.0,
                2017 to 3.0,
                2022 to 2.0,
                2021 to 1.0,
                2020 to 1.5
            )
        )

        val lastProvents = provents.lastYears(2)
        Assertions.assertEquals(2, lastProvents.size)
        Assertions.assertEquals(1.0, lastProvents[0])
        Assertions.assertEquals(2.0, lastProvents[1])
    }

    @Test
    fun `Should list provents of last 5 years when provents has exactly 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2019 to 5.0,
                2018 to 4.0,
                2022 to 2.0,
                2021 to 1.0,
                2020 to 1.5
            )
        )

        val lastProvents = provents.lastYears(5)
        Assertions.assertEquals(5, lastProvents.size)
        Assertions.assertEquals(4.0, lastProvents[0])
        Assertions.assertEquals(5.0, lastProvents[1])
        Assertions.assertEquals(1.5, lastProvents[2])
        Assertions.assertEquals(1.0, lastProvents[3])
        Assertions.assertEquals(2.0, lastProvents[4])
    }

    @Test
    fun `Should calculate average provents of last 5 years filling with zeros when provents has less than 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2022 to 2.0,
                2021 to 2.0,
                2020 to 1.0
            )
        )

        val averageProvents = provents.averageLastYears(5)
        Assertions.assertEquals(1.0, averageProvents)
    }

    @Test
    fun `Should calculate average provents of last 5 years when provents has more than 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2019 to 1.0,
                2018 to 1.0,
                2017 to 233.0,
                2022 to 1.0,
                2021 to 1.0,
                2020 to 1.0
            )
        )

        val averageProvents = provents.averageLastYears(5)
        Assertions.assertEquals(1.0, averageProvents)
    }

    @Test
    fun `Should calculate average provents of last 5 years when provents exactly 5 years`() {

        val provents = CompanyProvents(
            code = "TEST",
            yearProvents = mapOf(
                2019 to 1.3,
                2018 to 1.3,
                2022 to 1.3,
                2021 to 1.3,
                2020 to 1.3
            )
        )

        val averageProvents = provents.averageLastYears(5)
        Assertions.assertEquals(1.3, averageProvents)
    }
}
