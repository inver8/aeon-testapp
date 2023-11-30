package ru.aeon.testapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aeon.testapp.data.repository.AuthRepositoryImpl
import ru.aeon.testapp.data.repository.PaymentsRepositoryImpl
import ru.aeon.testapp.domain.repository.AuthRepository
import ru.aeon.testapp.domain.repository.PaymentsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    
    @Binds
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
    
    @Binds
    abstract fun bindPaymentsRepository(repositoryImpl: PaymentsRepositoryImpl): PaymentsRepository
}