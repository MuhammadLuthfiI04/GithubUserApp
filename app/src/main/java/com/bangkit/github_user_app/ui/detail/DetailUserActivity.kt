package com.bangkit.github_user_app.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bangkit.github_user_app.R
import com.bangkit.github_user_app.ui.fragment.FollowPagerAdapter
import com.bangkit.github_user_app.util.Constanta.EXTRA_AVATAR
import com.bangkit.github_user_app.util.Constanta.EXTRA_GIT_URL
import com.bangkit.github_user_app.util.Constanta.EXTRA_ID
import com.bangkit.github_user_app.util.Constanta.EXTRA_USERNAME
import com.bangkit.github_user_app.databinding.ActivityDetailUserBinding
import com.bangkit.github_user_app.util.Constanta
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Constanta

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val gitUrl = intent.getStringExtra(EXTRA_GIT_URL)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvLink.text = it.html_url
                    tvLink.setOnClickListener {
                        val intentToGit = Intent(Intent.ACTION_VIEW)
                        intentToGit.data = Uri.parse(gitUrl)
                        startActivity(intentToGit)
                    }
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvBlog.text = it.blog
                    tvRepository.text = "${it.public_repos}\nRepository"
                    tvGists.text = "${it.public_gists}\nGists"
                    tvFollowers.text = "${it.followers}\nFollowers"
                    tvFollowing.text = "${it.following}\nFollowing"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .placeholder(R.drawable.anim_progress_icon)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(imgAvatar)
                }
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(id,
                    username.toString(),
                    gitUrl.toString(),
                    avatarUrl.toString())
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

        val followPagerAdapter = FollowPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = followPagerAdapter
            tabsLayout.setupWithViewPager(viewPager)
        }
    }

}