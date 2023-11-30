package ru.aeon.testapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aeon.testapp.data.remote.repository.AuthRepositoryImpl
import ru.aeon.testapp.domain.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    
    @Binds
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}