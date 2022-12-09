package com.edwinkapkei.githubapi.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwinkapkei.githubapi.R
import com.edwinkapkei.githubapi.data.model.GithubFollower
import com.edwinkapkei.githubapi.databinding.ListItemFollowerBinding
import com.edwinkapkei.githubapi.databinding.ListItemLoadingBinding

class FollowerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val list: MutableList<GithubFollower?> = mutableListOf()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    fun updateList(newList: List<GithubFollower?>) {
        val tempList: MutableList<GithubFollower?> = mutableListOf()
        tempList.addAll(list)
        tempList.addAll(newList)

        val diffUtil = FollowerDiffUtil(this.list, tempList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(tempList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val binding = ListItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FollowerViewHolder(binding)
        } else {
            val binding = ListItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val follower = list[position]

        if (holder is FollowerViewHolder) {
            holder.binding.username.text = follower?.login
            Glide.with(holder.binding.avatar.context)
                .load(follower?.avatarUrl)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.binding.avatar)
        } else {

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    inner class FollowerViewHolder(val binding: ListItemFollowerBinding) : RecyclerView.ViewHolder(binding.root)
    inner class LoadingViewHolder(val binding: ListItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}