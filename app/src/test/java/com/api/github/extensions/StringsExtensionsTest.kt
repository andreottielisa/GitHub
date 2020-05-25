package com.api.github.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

internal class StringsExtensionsTest {

    @Test
    fun `Assert formatToDateAndHour`() {
        assertEquals("Quarta-feira, 20 de Maio de 2020", "2020-05-20T17:03:05Z".formatToDateAndHour())
        assertEquals("Quarta-feira, 20 de Maio de 2020", "2020-05-20".formatToDateAndHour("yyyy-MM-dd"))
        assertEquals("17/08/2020", "17/08/2020".formatToDateAndHour("yyyy-MM-dd"))
    }
}