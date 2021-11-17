package com.adasoraninda.bottomnav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private val _theme = MutableLiveData<Int>()
    val theme: LiveData<Int> get() = _theme

    init {
        getTheme()
    }

    fun saveTheme(theme: Int) {
        coroutineScope.launch {
            themePreferences.saveTheme(theme)
        }
    }

    private fun getTheme() {
        coroutineScope.launch {
            themePreferences.getTheme().collect {
                _theme.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

}