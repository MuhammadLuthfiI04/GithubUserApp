package com.bangkit.github_user_app.ui.settings

import androidx.lifecycle.*
import com.bangkit.github_user_app.room.SettingPreferences
import kotlinx.coroutines.launch


class SettingViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}