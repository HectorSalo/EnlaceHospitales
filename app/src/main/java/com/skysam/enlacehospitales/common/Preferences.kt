package com.skysam.enlacehospitales.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Preferences {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERENCES)

    private val PREFERENCE_NOTIFICATION = booleanPreferencesKey(Constants.NOTIFICATION)
    private val PREFERENCE_BIOMETRIC = booleanPreferencesKey(Constants.PREFERENCES_BIOMETRIC)
    private val PREFERENCE_EMAIL = stringPreferencesKey(Constants.EMAIL)
    private val PREFERENCE_PASSWORD = stringPreferencesKey(Constants.PASSWORD)

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

    fun isBiometricEnable(): Flow<Boolean> {
        return EnlaceHospitales.EnlaceHospitales.getContext().dataStore.data
            .map {
                it[PREFERENCE_BIOMETRIC] ?: false
            }
    }

    suspend fun setBiometric(status: Boolean) {
        EnlaceHospitales.EnlaceHospitales.getContext().dataStore.edit {
            it[PREFERENCE_BIOMETRIC] = status
        }
    }

    fun getEmailSaved(): Flow<String> {
        return EnlaceHospitales.EnlaceHospitales.getContext().dataStore.data
            .map {
                it[PREFERENCE_EMAIL] ?: ""
            }
    }

    suspend fun setEmailSaved(email: String) {
        EnlaceHospitales.EnlaceHospitales.getContext().dataStore.edit {
            it[PREFERENCE_EMAIL] = email
        }
    }

    fun getPasswordSaved(): Flow<String> {
        return EnlaceHospitales.EnlaceHospitales.getContext().dataStore.data
            .map {
                it[PREFERENCE_PASSWORD] ?: ""
            }
    }

    suspend fun setPasswordSaved(password: String) {
        EnlaceHospitales.EnlaceHospitales.getContext().dataStore.edit {
            it[PREFERENCE_PASSWORD] = password
        }
    }
}
