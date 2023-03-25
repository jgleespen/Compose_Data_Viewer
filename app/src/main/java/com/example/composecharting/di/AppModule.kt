package com.example.composecharting.di

import com.example.composecharting.data.DummyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
   @Singleton
   @Provides
   fun provideDummyRepository(): DummyRepository = DummyRepository()
}