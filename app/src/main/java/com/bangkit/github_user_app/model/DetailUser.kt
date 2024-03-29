package com.bangkit.github_user_app.model

data class DetailUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int
)