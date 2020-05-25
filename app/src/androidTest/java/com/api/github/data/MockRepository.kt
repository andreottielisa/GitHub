package com.api.github.data

import androidx.test.platform.app.InstrumentationRegistry
import com.api.github.data.model.PullRequestModelResponse
import com.api.github.data.model.SearchResponseModel
import com.api.github.domain.repository.GitHubRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


internal class MockRepository : GitHubRepository {

    private val firstPageMocked by lazy {
        Gson().fromJson<SearchResponseModel>(
            readJsonFile("repositories_page1"),
            SearchResponseModel::class.java
        )
    }

    private val secondPageMocked by lazy {
        Gson().fromJson<SearchResponseModel>(
            readJsonFile("repositories_page2"),
            SearchResponseModel::class.java
        )
    }

    private val pullRequestsMocked by lazy {
        val typeToken = object : TypeToken<List<PullRequestModelResponse>>() {}.type

        Gson().fromJson<List<PullRequestModelResponse>>(
            readJsonFile("mr"),
            typeToken
        )
    }

    override fun fetchRepo(page: Long): Single<SearchResponseModel>? = Single.just(
        when (page) {
            1L -> firstPageMocked
            else -> secondPageMocked
        }
    )

    override fun fetchPullRequests(
        owner: String,
        repository: String
    ): Single<List<PullRequestModelResponse>>? = Single.just(
        pullRequestsMocked
    )

    @Throws(IOException::class)
    private fun readJsonFile(filename: String): String? {
        val inputStream: InputStream =
            InstrumentationRegistry.getInstrumentation().context
                .assets.open("$filename.json")

        val br = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }
}