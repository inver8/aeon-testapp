package ru.aeon.testapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException

class EncryptedPreferences(
    private val context: Context, 
    private val filename: String
) {
    
    private lateinit var prefs: SharedPreferences
    
    init {
        try {
            init(context, filename)
        } catch (e: GeneralSecurityException) {
            reInit(context, filename)
        } catch (e: SecurityException) {
            reInit(context, filename)
        } catch (e: IOException) {
            reInit(context, filename)
        }
    }
    
    @Throws(GeneralSecurityException::class, SecurityException::class, IOException::class)
    private fun init(
        context: Context, 
        filename: String
    ) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        
        prefs = EncryptedSharedPreferences.create(
            context,
            filename,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        if (prefs.getInt(KEY_PREFS_VERSION, 0) != PREFS_VERSION) {
            val editor = prefs.edit()
            editor.clear()
            editor.putInt(KEY_PREFS_VERSION, PREFS_VERSION)
            editor.apply()
        }
    }
    
    private fun reInit(
        context: Context,
        filename: String
    ) {
        context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit().clear().apply()
        try {
            init(context, filename)
        } catch (ex: GeneralSecurityException) {
            Log.e(TAG, "EncryptedPrefs: ", ex)
        } catch (ex: SecurityException) {
            Log.e(TAG, "EncryptedPrefs: ", ex)
        } catch (ex: IOException) {
            Log.e(TAG, "EncryptedPrefs: ", ex)
        }
    }
    
    fun getApiToken(): String? {
        return prefs.getString(KEY_API_TOKEN, null)
    }
    
    fun setApiToken(apiToken: String?) {
        val editor = prefs.edit()
        editor.putString(KEY_API_TOKEN, apiToken)
        editor.apply()
    }
    
    fun clearApiToken() {
        val editor = prefs.edit()
        editor.remove(KEY_API_TOKEN)
        editor.apply()
    }
    
    companion object {
        private val TAG = EncryptedPreferences::class.java.simpleName
        
        private const val PREFS_VERSION = 1
        private const val KEY_PREFS_VERSION = "prefs_version"
        private const val KEY_API_TOKEN = "api_token"
        
    }
}