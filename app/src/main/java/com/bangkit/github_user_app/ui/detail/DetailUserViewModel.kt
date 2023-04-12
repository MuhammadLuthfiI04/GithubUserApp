package com.bangkit.github_user_app.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.github_user_app.api.RetrofitUser
import com.bangkit.github_user_app.model.FavoriteUserEntity
import com.bangkit.github_user_app.model.DetailUser
import com.bangkit.github_user_app.room.FavoriteUserDao
import com.bangkit.github_user_app.room.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUser>()

    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDb?.favoriteUserDao()

    fun setUserDetail(username: String?) {
        RetrofitUser.apiInstance
            .getDetailUser(username.toString())
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>,
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("Error", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return user
    }

    fun addToFavorite(id: Int, username: String, html_url: String, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUserEntity(
                id,
                username,
                html_url,
                avatar_url
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}