package com.edwinkapkei.githubapi.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinkapkei.githubapi.R
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.databinding.FragmentFollowersBinding
import com.edwinkapkei.githubapi.views.MainActivity
import com.edwinkapkei.githubapi.views.adapter.FollowerAdapter
import com.edwinkapkei.githubapi.views.viewmodel.UserViewModel

class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    lateinit var viewModel: UserViewModel
    lateinit var adapter: FollowerAdapter
    private var page = 1
    private var isLoading = false
    private var isLastPage = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersBinding.bind(view)

        val args: FollowersFragmentArgs by navArgs()
        val user = args.githubUser
        viewModel = (activity as MainActivity).viewModel

        adapter = FollowerAdapter()
        binding.followersRecycler.adapter = adapter
        binding.followersRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.followersRecycler.addOnScrollListener(this.onScrollListener)

        user.login?.let {
            viewModel.getUserFollowers(it, args.followType, 10, page)
        }

        observeFollowerResults()
    }

    private fun observeFollowerResults() {
        viewModel.userFollowers.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResourceStatus.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        if (it.isEmpty() || it.size < 10)
                            isLastPage = true

                        adapter.updateList(it)
                    }
                }
                is ResourceStatus.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(context, "An error occurred: $it", Toast.LENGTH_SHORT).show()
                    }
                }
                is ResourceStatus.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.followersRecycler.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedEnd

            if (shouldPaginate) {
                page++
                val args: FollowersFragmentArgs by navArgs()
                val user = args.githubUser
                user.login?.let {
                    viewModel.getUserFollowers(it, args.followType, 10, page)
                }

                isLoading = true

            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        //binding.followersRecycler.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        //binding.followersRecycler.visibility = View.VISIBLE
        binding.progressbar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.userFollowers.postValue(null)
    }
}