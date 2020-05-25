package com.api.github.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.api.github.R
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.extensions.navigateTo
import com.api.github.extensions.show
import com.api.github.presentation.fragment.ListRepoFragment
import com.api.github.presentation.fragment.PullRequestsFragment
import com.api.github.utils.REPO
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory()
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) return
        showListRepo()
    }

    private fun showListRepo() {
        navigateTo(R.id.contentFragment, ListRepoFragment::class.java, addToBackStack = false)
    }

    internal fun showPullRequests(item: RepoGitHubEntity?) {
        val args = Bundle().apply {
            putParcelable(REPO, item)
        }
        navigateTo(R.id.contentFragment, PullRequestsFragment::class.java, args = args)
    }

    internal fun showLoad() {
        progressBar?.show(true)
        progressBar?.requestFocus()
        progressBar?.announceForAccessibility(getString(R.string.accessibility_progress_bar))
    }

    internal fun hideLoad() {
        progressBar?.show(false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
