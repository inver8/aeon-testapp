package ru.aeon.testapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aeon.testapp.data.local.preferences.EncryptedPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EncryptedPreferencesModule {
    
    @Singleton
    @Provides
    fun provideEncryptedPreferences(@ApplicationContext context: Context) = EncryptedPreferences(context, "private_prefs")
    
}