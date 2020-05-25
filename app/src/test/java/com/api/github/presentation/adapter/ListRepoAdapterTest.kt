package com.api.github.presentation.adapter

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.github.R
import com.api.github.mockedRepoGitHubEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ListRepoAdapterTest : AutoCloseKoinTest() {

    private lateinit var adapterTest: ListRepoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var activity: Activity

    @Before
    fun setupTest() {
        activity = Robolectric.buildActivity(Activity::class.java).get()
        adapterTest = ListRepoAdapter(activity, arrayListOf(mockedRepoGitHubEntity)) {
            assertNotNull(it)
        }

        recyclerView = RecyclerView(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapterTest
    }

    @Test
    fun `Assert itemCount`() {
        assertEquals(2, adapterTest.itemCount)
    }

    @Test
    fun `Assert getItemViewType`() {
        assertEquals(1, adapterTest.getItemViewType(0))
        assertEquals(2, adapterTest.getItemViewType(1))
    }

    @Test
    fun `Assert onCreateViewHolder`() {
        val viewHolderLoading = adapterTest.onCreateViewHolder(recyclerView, 2)
        val viewHolderItem = adapterTest.onCreateViewHolder(recyclerView, 1)

        assertEquals(ListRepoAdapter.ViewHolderLoading::class.java, viewHolderLoading.javaClass)
        assertEquals(ListRepoAdapter.ViewHolder::class.java, viewHolderItem.javaClass)
    }

    @Test
    fun `Assert onBindViewHolder ViewHolderLoading`() {
        val viewHolderLoading = adapterTest.onCreateViewHolder(recyclerView, 2)
        val progressBar = viewHolderLoading.itemView.findViewById<View>(R.id.item_progressBar)

        adapterTest.onBindViewHolder(viewHolderLoading, 1)

        assertEquals(View.VISIBLE, progressBar.visibility)
    }

    @Test
    fun `Assert onBindViewHolder ViewHolderItem`() {
        val viewHolder = adapterTest.onCreateViewHolder(recyclerView, 1)
        adapterTest.onBindViewHolder(viewHolder, 0)

        val username: TextView? = viewHolder.itemView.findViewById(R.id.item_username)
        val descriptionRepo: TextView? = viewHolder.itemView.findViewById(R.id.item_description)
        val repoName: TextView? = viewHolder.itemView.findViewById(R.id.item_repo_name)
        val numForks: TextView? = viewHolder.itemView.findViewById(R.id.item_num_forks)
        val numStarts: TextView? = viewHolder.itemView.findViewById(R.id.item_num_starts)

        assertEquals(activity.getString(R.string.stargazers_count_description, mockedRepoGitHubEntity.stargazersCount), numStarts?.contentDescription)
        assertEquals(activity.getString(R.string.forks_description, mockedRepoGitHubEntity.forksCount), numForks?.contentDescription)
        assertEquals(mockedRepoGitHubEntity.username, username?.text?.toString())
        assertEquals(mockedRepoGitHubEntity.nameRepo, repoName?.text?.toString())
        assertEquals(mockedRepoGitHubEntity.descriptionRepo, descriptionRepo?.text?.toString())
        assertTrue(viewHolder.itemView.performClick())
    }

    @Test
    fun `Assert setItems and LastPage`() {
        val adapterTest = ListRepoAdapter(activity, arrayListOf(mockedRepoGitHubEntity)) {
            assertNotNull(it)
        }

        adapterTest.lastPage = true
        adapterTest.items = arrayListOf(mockedRepoGitHubEntity)

        assertEquals(1, adapterTest.itemCount)
        assertTrue(adapterTest.lastPage)
    }

}