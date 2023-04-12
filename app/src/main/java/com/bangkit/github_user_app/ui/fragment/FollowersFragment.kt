package com.bangkit.github_user_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.github_user_app.R
import com.bangkit.github_user_app.ui.main.MainAdapter
import com.bangkit.github_user_app.databinding.FragmentFollowBinding
import com.bangkit.github_user_app.model.User
import com.bangkit.github_user_app.ui.detail.DetailUserActivity
import com.bangkit.github_user_app.util.Constanta.EXTRA_AVATAR
import com.bangkit.github_user_app.util.Constanta.EXTRA_GIT_URL
import com.bangkit.github_user_app.util.Constanta.EXTRA_ID
import com.bangkit.github_user_app.util.Constanta.EXTRA_USERNAME

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = MainAdapter()

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                intent.putExtra(EXTRA_ID, data.id)
                intent.putExtra(EXTRA_GIT_URL, data.html_url)
                intent.putExtra(EXTRA_AVATAR, data.avatar_url)
                context?.startActivity(intent, Bundle())
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}