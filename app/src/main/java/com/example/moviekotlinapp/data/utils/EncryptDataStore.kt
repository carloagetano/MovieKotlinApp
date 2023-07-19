package com.example.moviekotlinapp.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.moviekotlinapp.data.utils.Constants.SHARED_PREF_FILE

abstract class EncryptDataStore : SharedPreferences {

    companion object {

        @Volatile
        private var INSTANCE: SharedPreferences? = null

        private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        fun getInstance(context: Context): SharedPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = EncryptedSharedPreferences.create(
                    SHARED_PREF_FILE,
                    masterKey,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                INSTANCE = instance

                instance
            }
        }
    }
}