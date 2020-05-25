package com.api.github.data.model

import com.api.github.mockedPullRequestModelResponse
import com.api.github.mockedUser
import org.junit.Assert.assertEquals
import org.junit.Test

internal class PullRequestModelTest {

    @Test
    fun `Assert User`() {
        val user = mockedUser
        assertEquals("xujianhai666", user.username)
        assertEquals("https://avatars2.githubusercontent.com/u/52450794?v=4", user.avatarUrl)
    }

    @Test
    fun `Assert PullRequestModelResponse`() {
        val response = mockedPullRequestModelResponse

        assertEquals(39184L, response.number)
        assertEquals("open", response.state)
        assertEquals("container/list: Add elementPool for alloc new element", response.title)
        assertEquals( "Check GOOS/GOARCH pair after compiler binary exists check.\r\n\r\nFixes #24398 ", response.body)
        assertEquals("2020-05-20T17:03:05Z", response.createdDate)
        assertEquals("2020-05-21T21:22:52Z", response.updatedDate)
        assertEquals(mockedUser, response.user)
    }
}