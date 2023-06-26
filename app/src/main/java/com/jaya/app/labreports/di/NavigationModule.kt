package com.jaya.app.labreports.di

import com.jaya.app.labreports.presentation.ui.navigation.RouteNavigator
import com.jaya.app.labreports.presentation.ui.navigation.navigator.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object NavigationModule {

    @Provides
    @ViewModelScoped
    fun bindRouteNavigator(): RouteNavigator = AppNavigator()
}