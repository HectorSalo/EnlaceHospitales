package com.skysam.enlacehospitales.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.skysam.enlacehospitales.dataClasses.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Preferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERENCES)
    private val sharedPref = EnlaceHospitales.EnlaceHospitales.getContext().getSharedPreferences(
        Constants.PREFERENCES, Context.MODE_PRIVATE)

    private val PREFERENCE_NOTIFICATION = booleanPreferencesKey(Constants.PREFERENCES_NOTIFICATION)
    private val PREFERENCE_NOTIFICATIONS = pre(Constants.PREFERENCES_NOTIFICATION)

    fun getNotificationStatus(): Flow<Boolean> {
        return EnlaceHospitales.EnlaceHospitales.getContext().dataStore.data
            .map {
                it[PREFERENCE_NOTIFICATION] ?: true
            }
    }

    suspend fun changeNotificationStatus(status: Boolean) {
        EnlaceHospitales.EnlaceHospitales.getContext().dataStore.edit {
            it[PREFERENCE_NOTIFICATION] = status
        }
    }

    fun getSession(): Flow<User?> {
        return EnlaceHospitales.EnlaceHospitales.getContext().dataStore.data
            .map {
                it[PREFERENCE_NOTIFICATION] ?: null
            }
    }

    suspend fun changeNotificationStatus(status: Boolean) {
        EnlaceHospitales.EnlaceHospitales.getContext().dataStore.edit {
            it[PREFERENCE_NOTIFICATION] = status
        }
    }
}
