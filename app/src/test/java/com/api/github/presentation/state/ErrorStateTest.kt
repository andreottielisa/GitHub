package com.api.github.presentation.state

import com.api.github.R
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorStateTest {

    @Test
    fun `Assert ShowError`() {
        val state = ErrorState.ShowError(R.string.app_name)
        assertEquals(R.string.app_name, state.idRes)
    }
}