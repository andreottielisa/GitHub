package com.api.github.presentation.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.api.github.R
import com.api.github.domain.entities.PullRequestsEntity
import com.api.github.extensions.loadUrl

internal class PullRequestAdapter(
    private val context: Context?,
    private val items: List<PullRequestsEntity>
) :
    RecyclerView.Adapter<PullRequestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pull_requests, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items.getOrNull(position)
        viewHolder.date?.text = context?.getString(R.string.date_count_description, item?.createdDate)
        viewHolder.title?.text = item?.title
        viewHolder.description?.text = item?.description
        viewHolder.userAvatar?.loadUrl(item?.avatarUrl)
        viewHolder.user?.text = item?.username
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.findViewById(R.id.item_pull_name)
        val description: TextView? = itemView.findViewById(R.id.item_pull_description)
        val date: TextView? = itemView.findViewById(R.id.item_pull_date)
        val user: TextView? = itemView.findViewById(R.id.item_pull_username)
        val userAvatar: ImageView? = itemView.findViewById(R.id.item_pull_user_avatar)
    }
}