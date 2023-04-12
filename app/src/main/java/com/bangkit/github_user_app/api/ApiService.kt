package com.bangkit.github_user_app.api

import com.bangkit.github_user_app.BuildConfig
import com.bangkit.github_user_app.model.DetailUser
import com.bangkit.github_user_app.model.User
import com.bangkit.github_user_app.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val mySuperSecretKey = BuildConfig.API_KEY

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $mySuperSecretKey")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $mySuperSecretKey")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $mySuperSecretKey")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $mySuperSecretKey")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}