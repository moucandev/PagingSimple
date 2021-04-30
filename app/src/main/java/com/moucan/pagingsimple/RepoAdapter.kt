package com.moucan.pagingsimple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moucan.common.repo.GitRepo
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class RepoAdapter @Inject constructor(): PagingDataAdapter<GitRepo, RepoAdapter.ViewHolder>(COMPARATOR) {
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GitRepo>() {
            override fun areItemsTheSame(oldItem: GitRepo, newItem: GitRepo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GitRepo, newItem: GitRepo): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var mClickListener: OnItemClickListener

    fun setOnClickListener(mOnClickListener: OnItemClickListener) {
        this.mClickListener = mOnClickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text)
        val description: TextView = itemView.findViewById(R.id.description_text)
        val starCount: TextView = itemView.findViewById(R.id.star_count_text)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let { re ->
            holder.name.text = re.name
            holder.description.text = re.description
            holder.starCount.text = re.starCount.toString()
            holder.itemView.setOnClickListener { mClickListener.onClick(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }
    interface OnItemClickListener {
        fun onClick(view: View)
    }
}