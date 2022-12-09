package com.edwinkapkei.githubapi.views.adapter

import androidx.recyclerview.widget.DiffUtil
import com.edwinkapkei.githubapi.data.model.GithubFollower

class FollowerDiffUtil(private val oldList: MutableList<GithubFollower?>, private val newList: List<GithubFollower?>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.login == newList[newItemPosition]?.login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}