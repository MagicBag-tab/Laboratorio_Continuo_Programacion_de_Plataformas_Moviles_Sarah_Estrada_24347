package com.magicbag.laboratorio_continuo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val loggedInKey = booleanPreferencesKey("logged_in")
    private val userNameKey = stringPreferencesKey("user_name")

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[loggedInKey] ?: false
    }

    val userName: Flow<String> = dataStore.data.map { preferences ->
        preferences[userNameKey] ?: ""
    }

    suspend fun setLoggedIn(userName: String) {
        dataStore.edit { preferences ->
            preferences[loggedInKey] = true
            preferences[userNameKey] = userName
        }
    }

    suspend fun getLoggedIn(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[loggedInKey] ?: false
    }

    suspend fun getUserName(): String {
        val preferences = dataStore.data.first()
        return preferences[userNameKey] ?: ""
    }

    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}