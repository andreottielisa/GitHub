package com.api.github.presentation.state

import com.api.github.mockedPullRequestsEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PullRequestsStateTest {

    @Test
    fun `Assert SuccessPullRequests`() {
        val expected = arrayListOf(mockedPullRequestsEntity)
        val state = PullRequestsState.SuccessPullRequests(expected)
        assertEquals(expected, state.items)
    }

    @Test
    fun `Assert EmptyPullRequests`() {
        assertNotNull(PullRequestsState.EmptyPullRequests)
    }

}