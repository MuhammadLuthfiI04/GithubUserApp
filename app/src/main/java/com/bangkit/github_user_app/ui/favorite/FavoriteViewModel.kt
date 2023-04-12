package com.bangkit.github_user_app.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.github_user_app.model.FavoriteUserEntity
import com.bangkit.github_user_app.room.FavoriteUserDao
import com.bangkit.github_user_app.room.UserDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDb?.favoriteUserDao()

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>? {
        return userDao?.getFavoriteUser()
    }
}