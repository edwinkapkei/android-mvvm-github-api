package com.edwinkapkei.githubapi.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.edwinkapkei.githubapi.R
import com.edwinkapkei.githubapi.data.model.GithubUser
import com.edwinkapkei.githubapi.data.utilities.ResourceStatus
import com.edwinkapkei.githubapi.databinding.FragmentSearchBinding
import com.edwinkapkei.githubapi.views.MainActivity
import com.edwinkapkei.githubapi.views.viewmodel.UserViewModel
import timber.log.Timber

class SearchFragment : Fragment() {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        binding.searchButton.setOnClickListener {
            val username = binding.search.text.toString()
            if (username.isEmpty()) {
                binding.searchLayout.error = "Please enter username"
                binding.searchLayout.isErrorEnabled = true
            } else {
                binding.searchLayout.isErrorEnabled = false
                Timber.e("Getting user")
                viewModel.getUser(username)
            }
        }

        binding.followers.setOnClickListener {
            val response = viewModel.githubUser.value
            response?.data?.let {
                val bundle = Bundle().apply {
                    putString("follow_type", "followers")
                    putSerializable("github_user", it)
                }

                findNavController().navigate(R.id.action_searchFragment_to_followersFragment, bundle)
            }
        }

        binding.following.setOnClickListener {
            val response = viewModel.githubUser.value
            response?.data?.let {
                val bundle = Bundle().apply {
                    putString("follow_type", "following")
                    putSerializable("github_user", it)
                }

                findNavController().navigate(R.id.action_searchFragment_to_followersFragment, bundle)
            }
        }

        observeSearchResult()
    }

    private fun observeSearchResult() {
        viewModel.githubUser.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResourceStatus.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        updateUI(it)
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

    private fun updateUI(githubUser: GithubUser) {
        binding.userInfo.visibility = View.VISIBLE

        binding.username.text = getString(R.string.username, githubUser.login)
        binding.name.text = githubUser.name
        binding.bio.text = githubUser.bio
        binding.repos.text = githubUser.publicRepos.toString()
        binding.createdOn.text = getString(R.string.account_created_on, githubUser.createdAt)
        binding.followers.text = getString(R.string.followers, githubUser.followers)
        binding.following.text = getString(R.string.following, githubUser.following)

        Glide.with(binding.avatar.context)
            .load(githubUser.avatarUrl)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .into(binding.avatar)

    }

    private fun showProgressBar() {
        binding.userInfo.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = View.INVISIBLE
    }
}