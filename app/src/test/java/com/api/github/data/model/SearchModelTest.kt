package com.api.github.data.model

import com.api.github.mockedOwner
import com.api.github.mockedRepo
import com.api.github.mockedSearchResponseModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

internal class SearchModelTest {

    @Test
    fun `Assert Repo`() {
        val repo = mockedRepo

        assertEquals(121395510L, repo.id)
        assertEquals("CS-Notes", repo.nameRepo)
        assertEquals("CyC2018/CS-Notes", repo.fullNameRepo)
        assertEquals(
            ":books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++",
            repo.description
        )
        assertEquals(33015L, repo.forksCount)
        assertEquals(101407L, repo.stargazersCount)
        assertEquals(mockedOwner, repo.owner)

    }

    @Test
    fun `Assert Owner`() {
        val owner = mockedOwner

        assertEquals(36260787L, owner.id)
        assertEquals("CyC2018", owner.username)
        assertEquals("https://avatars3.githubusercontent.com/u/36260787?v=4", owner.avatarUrl)
    }

    @Test
    fun `Assert SearchResponseModel`() {
        val searchResponseModel = mockedSearchResponseModel

        assertEquals(7879339L, searchResponseModel.totalCount)
        assertNotNull(searchResponseModel.items)
    }
}