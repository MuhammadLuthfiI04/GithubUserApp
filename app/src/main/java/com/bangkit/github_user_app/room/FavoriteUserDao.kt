package com.bangkit.github_user_app.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bangkit.github_user_app.model.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun addToFavorite(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}