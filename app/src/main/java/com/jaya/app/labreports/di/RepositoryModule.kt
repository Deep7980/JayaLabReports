package com.jaya.app.labreports.di

import com.jaya.app.labreports.core.domain.repositories.LoginRepository
import com.jaya.app.labreports.core.domain.repositories.SplashRepository
import com.jaya.app.labreports.data.repoimpl.LoginRepositoryImpl
import com.jaya.app.labreports.data.repoimpl.SplashRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSplashRepository(impl: SplashRepositoryImpl): SplashRepository

    @Binds
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository
}