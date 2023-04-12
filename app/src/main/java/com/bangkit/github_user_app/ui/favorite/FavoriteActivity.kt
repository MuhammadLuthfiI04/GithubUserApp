package com.bangkit.github_user_app.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.github_user_app.ui.main.MainAdapter
import com.bangkit.github_user_app.databinding.ActivityFavoriteBinding
import com.bangkit.github_user_app.model.FavoriteUserEntity
import com.bangkit.github_user_app.model.User
import com.bangkit.github_user_app.ui.detail.DetailUserActivity
import com.bangkit.github_user_app.util.Constanta.EXTRA_AVATAR
import com.bangkit.github_user_app.util.Constanta.EXTRA_GIT_URL
import com.bangkit.github_user_app.util.Constanta.EXTRA_ID
import com.bangkit.github_user_app.util.Constanta.EXTRA_USERNAME

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(EXTRA_USERNAME, data.login)
                    it.putExtra(EXTRA_ID, data.id)
                    it.putExtra(EXTRA_GIT_URL, data.html_url)
                    it.putExtra(EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
    }

    private fun mapList(it: List<FavoriteUserEntity>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in it) {
            val userMapped = User(
                user.login,
                user.id,
                user.html_url,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}