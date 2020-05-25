package com.api.github.presentation.state

import com.api.github.mockedRepoGitHubEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ListRepoStateTest {
    @Test
    fun `Assert SuccessRepoList`() {
        val expected = arrayListOf(mockedRepoGitHubEntity)
        val state = ListRepoState.SuccessRepoList(expected, false)
        assertEquals(expected, state.items)
        assertFalse(state.lastPage)
    }
}