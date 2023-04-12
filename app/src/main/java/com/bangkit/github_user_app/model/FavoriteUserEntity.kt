package com.bangkit.github_user_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUserEntity(
    @field:ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "html_url")
    val html_url: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatar_url: String,

    ) : Serializable
