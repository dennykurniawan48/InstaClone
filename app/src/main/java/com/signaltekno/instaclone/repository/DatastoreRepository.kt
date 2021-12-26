package com.signaltekno.instaclone.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class DatastoreRepository @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore
    val flowEmail: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[emailKey] ?: ""
        }

    val flowName: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[nameKey] ?: ""
        }

    val flowImage: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[imageKey] ?: ""
        }

    suspend fun saveEmail(email: String) {
        dataStore.edit { settings ->
            settings[emailKey] = email
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit { settings ->
            settings[nameKey] = name
        }
    }

    suspend fun saveImage(imageUrl: String) {
        dataStore.edit { settings ->
            settings[imageKey] = imageUrl
        }
    }

    companion object PreferencesKey {
        val emailKey = stringPreferencesKey("_email")
        val nameKey = stringPreferencesKey("_name")
        val imageKey= stringPreferencesKey("_image")
    }
}