package com.api.github.presentation.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.api.github.R
import com.api.github.domain.entities.RepoGitHubEntity
import com.api.github.extensions.loadUrl
import com.api.github.extensions.show
import com.api.github.utils.STRING_DEFAULT

internal class ListRepoAdapter(
    private val context: Context?,
    var items: List<RepoGitHubEntity>,
    var lastPage: Boolean = false,
    private val action: (RepoGitHubEntity?) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class ViewType(val type: Int) {
        ITEM(1), LOADING(2)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.getOrNull(position) != null)
            ViewType.ITEM.type
        else
            ViewType.LOADING.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.ITEM.type -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_repo, parent, false)
            )
            else -> ViewHolderLoading(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_repo_load, parent, false)
            )
        }

    override fun getItemCount(): Int = when (lastPage) {
        true -> items.size
        else -> items.size + 1
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ViewHolder) {
            viewHolder.bind(items.getOrNull(position))
        } else if (viewHolder is ViewHolderLoading) {
            viewHolder.progressBar?.show(true)
            viewHolder.progressBar?.announceForAccessibility(context?.getString(R.string.accessibility_progress_bar))
        }
    }

    inner class ViewHolderLoading(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: View? = itemView.findViewById(R.id.item_progressBar)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView? = itemView.findViewById(R.id.item_user_avatar)
        private val username: TextView? = itemView.findViewById(R.id.item_username)
        private val descriptionRepo: TextView? = itemView.findViewById(R.id.item_description)
        private val repoName: TextView? = itemView.findViewById(R.id.item_repo_name)
        private val numForks: TextView? = itemView.findViewById(R.id.item_num_forks)
        private val numStarts: TextView? = itemView.findViewById(R.id.item_num_starts)

        fun bind(item: RepoGitHubEntity?) {
            avatar?.loadUrl(item?.avatar ?: STRING_DEFAULT)
            username?.text = item?.username
            repoName?.text = item?.nameRepo
            descriptionRepo?.text = item?.descriptionRepo
            numForks?.text = item?.forksCount
            numStarts?.text = item?.stargazersCount

            numStarts?.contentDescription =
                context?.getString(R.string.stargazers_count_description, item?.stargazersCount)
            numForks?.contentDescription =
                context?.getString(R.string.forks_description, item?.forksCount)

            itemView.setOnClickListener {
                action(item)
            }
        }
    }
}