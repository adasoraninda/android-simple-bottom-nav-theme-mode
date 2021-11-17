package com.adasoraninda.bottomnav

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.theme.observe(viewLifecycleOwner) {
            buttonSwitch(R.id.switch_mode)?.isChecked = it == AppCompatDelegate.MODE_NIGHT_YES
        }

        buttonSwitch(R.id.switch_mode)?.setOnCheckedChangeListener { _, b ->
            Log.d("Settings", "$b")
            val mode = if (b) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            viewModel.saveTheme(mode)
        }

    }

    private fun buttonSwitch(@IdRes id: Int): SwitchMaterial? = view?.findViewById(id)

}

class ThemePreferences(
    private val datastore: DataStore<Preferences>
) {

    private val key = intPreferencesKey("key")

    suspend fun saveTheme(theme: Int) {
        datastore.edit { settings ->
            val value = settings[key]
            if (value != theme) settings[key] = theme
        }
    }

    fun getTheme(): Flow<Int?> {
        return datastore.data.map { settings ->
            settings[key]
        }
    }

}

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "Store")