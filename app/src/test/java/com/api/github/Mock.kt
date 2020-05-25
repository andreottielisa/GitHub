package com.api.github

import com.api.github.data.model.*
import com.api.github.domain.entities.PullRequestsEntity
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.domain.entities.RepositoriesEntity

internal val mockedOwner = Owner(
    username = "CyC2018",
    avatarUrl = "https://avatars3.githubusercontent.com/u/36260787?v=4",
    id = 36260787
)

internal val mockedRepo = Repo(
    id = 121395510,
    nameRepo = "CS-Notes",
    fullNameRepo = "CyC2018/CS-Notes",
    description = ":books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++",
    forksCount = 33015,
    stargazersCount = 101407,
    owner = mockedOwner
)

internal val mockedSearchResponseModel = SearchResponseModel(
    totalCount = 7879339,
    items = arrayListOf(mockedRepo)
)

internal val mockedRepoGitHubEntity = RepoGitHubEntity(
    nameRepo = "CS-Notes",
    descriptionRepo = ":books: 技术面试必备基础知识、Leetcode、计算机操作系统、计算机网络、系统设计、Java、Python、C++",
    username = "CyC2018",
    avatar = "https://avatars3.githubusercontent.com/u/36260787?v=4",
    forksCount = "33015",
    stargazersCount = "101407"
)

internal val mockedRepositoriesEntity = RepositoriesEntity(
    totalCount = 7879339,
    items = arrayListOf(mockedRepoGitHubEntity)
)

internal val mockedUser = User(
    username = "xujianhai666",
    avatarUrl = "https://avatars2.githubusercontent.com/u/52450794?v=4"
)

internal val mockedPullRequestModelResponse = PullRequestModelResponse(
    number = 39184,
    state = "open",
    title = "container/list: Add elementPool for alloc new element",
    body = "Check GOOS/GOARCH pair after compiler binary exists check.\r\n\r\nFixes #24398 ",
    createdDate = "2020-05-20T17:03:05Z",
    updatedDate = "2020-05-21T21:22:52Z",
    user = mockedUser
)

internal val mockedPullRequestsEntity = PullRequestsEntity(
    title = "container/list: Add elementPool for alloc new element",
    description = "Check GOOS/GOARCH pair after compiler binary exists check.\r\n\r\nFixes #24398 ",
    username = "xujianhai666",
    avatarUrl = "https://avatars2.githubusercontent.com/u/52450794?v=4",
    createdDate = "Quarta-feira, 20 de Maio de 2020"
)