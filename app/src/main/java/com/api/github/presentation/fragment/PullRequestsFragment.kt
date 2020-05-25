package com.api.github.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.github.R
import com.api.github.commons.AbstractFragment
import com.api.github.di.loadModulePullRequests
import com.api.github.domain.entities.PullRequestsEntity
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.extensions.show
import com.api.github.presentation.adapter.PullRequestAdapter
import com.api.github.presentation.state.ErrorState
import com.api.github.presentation.state.LoadState
import com.api.github.presentation.state.PullRequestsState
import com.api.github.presentation.viewmodel.PullRequestsViewModel
import com.api.github.utils.REPO
import com.api.github.utils.STRING_DEFAULT
import kotlinx.android.synthetic.main.fragment_list_repo.recyclerView
import kotlinx.android.synthetic.main.fragment_pull_requests.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PullRequestsFragment : AbstractFragment() {

    private val repoEntity by lazy { arguments?.getParcelable(REPO) as? RepoGitHubEntity }
    private val viewModel: PullRequestsViewModel by viewModel()

    init {
        loadModulePullRequests()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pull_requests, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(repoEntity?.nameRepo ?: STRING_DEFAULT, displayHomeAsUp = true)

        if (repoEntity == null) {
            showScreenAlert(
                R.string.mr_error_list,
                R.drawable.ic_error_red_24dp
            )
            return
        }

        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        initObservers()
        viewModel.getPullRequests(repoEntity)
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is PullRequestsState.SuccessPullRequests -> showList(state.items)
                is PullRequestsState.EmptyPullRequests -> showScreenAlert(
                    R.string.mr_empty_list,
                    R.drawable.ic_info_orange_24dp
                )
            }
        })

        viewModel.stateError.observe(viewLifecycleOwner, Observer { state ->
            if (state is ErrorState.ShowError)
                showScreenAlert(
                    state.idRes,
                    R.drawable.ic_error_red_24dp
                )
        })

        viewModel.stateLoad.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is LoadState.ShowLoading -> showLoad()
                is LoadState.HideLoading -> hideLoad()
            }
        })
    }

    private fun showList(items: List<PullRequestsEntity>) {
        recyclerView?.adapter = PullRequestAdapter(context, items)
        recyclerView?.announceForAccessibility(
            getString(
                R.string.accessibility_list_count_pull_requests,
                recyclerView?.adapter?.itemCount
            )
        )
    }

    private fun showScreenAlert(@StringRes idRes: Int, @DrawableRes imageRes: Int) {
        alertView?.setActionButton {
            activity?.onBackPressed()
        }

        alertView?.setIcon(imageRes)
        alertView?.setDescription(idRes)
        recyclerView?.show(false)
        alertView?.show(true)
    }
}