package com.api.github.presentation.adapter

import android.app.Activity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.github.R
import com.api.github.mockedPullRequestsEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PullRequestAdapterTest : AutoCloseKoinTest() {

    private lateinit var adapterTest: PullRequestAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var activity: Activity

    @Before
    fun setupTest() {
        activity = Robolectric.buildActivity(Activity::class.java).get()
        adapterTest = PullRequestAdapter(activity, arrayListOf(mockedPullRequestsEntity))

        recyclerView = RecyclerView(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapterTest
    }

    @Test
    fun `Assert itemCount`() {
       assertEquals(1, adapterTest.itemCount)
    }

    @Test
    fun `Assert onCreateViewHolder`() {
        val viewHolder = adapterTest.onCreateViewHolder(recyclerView, 0)
        assertEquals(PullRequestAdapter.ViewHolder::class.java, viewHolder.javaClass)
    }

    @Test
    fun `Assert onBindViewHolder`() {
        val viewHolder = adapterTest.onCreateViewHolder(recyclerView, 0)
        adapterTest.onBindViewHolder(viewHolder, 0)

        val title: TextView? = viewHolder.itemView.findViewById(R.id.item_pull_name)
        val description: TextView? = viewHolder.itemView.findViewById(R.id.item_pull_description)
        val date: TextView? = viewHolder.itemView.findViewById(R.id.item_pull_date)
        val user: TextView? = viewHolder.itemView.findViewById(R.id.item_pull_username)

        assertEquals(mockedPullRequestsEntity.title, title?.text?.toString())
        assertEquals(mockedPullRequestsEntity.description, description?.text?.toString())
        assertEquals(activity.getString(R.string.date_count_description, mockedPullRequestsEntity.createdDate), date?.text?.toString())
        assertEquals(mockedPullRequestsEntity.username, user?.text?.toString())
    }


}