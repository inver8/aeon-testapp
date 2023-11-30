package ru.aeon.testapp.data.local

import ru.aeon.testapp.data.local.preferences.EncryptedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiTokenProvider @Inject constructor(
    private val encryptedPrefs: EncryptedPreferences
) {
    
    private var apiToken: String? = encryptedPrefs.getApiToken() 
        
    fun getApiToken() = apiToken
    
    fun setApiToken(apiToken: String?) {
        this.apiToken = apiToken
        encryptedPrefs.setApiToken(apiToken)
    }
    
    fun isTokenExist(): Boolean {
        return !apiToken.isNullOrBlank()
    }
    
    fun clearApiToken() {
        apiToken = null
        encryptedPrefs.clearApiToken()
    }
}