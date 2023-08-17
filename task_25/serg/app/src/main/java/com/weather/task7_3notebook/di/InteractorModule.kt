package com.weather.task7_3notebook.di

import com.weather.task7_3notebook.domain.interactor.CityInteractor
import com.weather.task7_3notebook.domain.interactor.CityInteractorImpl
import com.weather.task7_3notebook.domain.interactor.ContactInteractor
import com.weather.task7_3notebook.domain.interactor.ContactInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorModule {
    @Binds
    abstract fun providesCityInteractor(impl: CityInteractorImpl): CityInteractor

    @Binds
    abstract fun providesContactInteractor(impl: ContactInteractorImpl): ContactInteractor
}
