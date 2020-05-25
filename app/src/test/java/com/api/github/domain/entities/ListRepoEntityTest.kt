package com.api.github.domain.entities

import com.api.github.mockedRepoGitHubEntity
import com.api.github.mockedRepositoriesEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

internal class ListRepoEntityTest {

    @Test
    fun `Assert RepoGitHubEntity`() {
        val item = mockedRepoGitHubEntity

        assertEquals("CS-Notes", item.nameRepo)
        assertEquals(":books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++", item.descriptionRepo)
        assertEquals("CyC2018", item.username)
        assertEquals("https://avatars3.githubusercontent.com/u/36260787?v=4", item.avatar)
        assertEquals("33015", item.forksCount)
        assertEquals("101407", item.stargazersCount)
    }

    @Test
    fun `Assert RepositoriesEntity`() {
        val it = mockedRepositoriesEntity

        assertEquals(7879339L, it.totalCount)
        assertNotNull(it.items)
    }
}