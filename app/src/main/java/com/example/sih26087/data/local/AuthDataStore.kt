package com.example.sih26087.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.sih26087.data.model.UserProfile
import com.example.sih26087.data.model.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_USER_PROFILE = stringPreferencesKey("user_profile")
    }

    val authToken: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_TOKEN]
    }

    val userProfile: Flow<UserProfile?> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_PROFILE]?.let {
            try {
                json.decodeFromString<UserProfile>(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun saveSession(token: String, profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
            prefs[KEY_USER_PROFILE] = json.encodeToString(UserProfile.serializer(), profile)
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
            prefs.remove(KEY_USER_PROFILE)
        }
    }
}
