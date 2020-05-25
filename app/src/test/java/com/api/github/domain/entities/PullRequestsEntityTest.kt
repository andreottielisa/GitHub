package com.api.github.domain.entities

import com.api.github.mockedPullRequestsEntity
import org.junit.Assert.assertEquals
import org.junit.Test

internal class PullRequestsEntityTest {

    @Test
    fun `Assert PullRequestsEntityTest`() {
        val entity = mockedPullRequestsEntity

        assertEquals("container/list: Add elementPool for alloc new element", entity.title)
        assertEquals(
            "Check GOOS/GOARCH pair after compiler binary exists check.\r\n\r\nFixes #24398 ",
            entity.description
        )
        assertEquals("xujianhai666", entity.username)
        assertEquals("https://avatars2.githubusercontent.com/u/52450794?v=4", entity.avatarUrl)
        assertEquals("Quarta-feira, 20 de Maio de 2020", entity.createdDate)
    }
}