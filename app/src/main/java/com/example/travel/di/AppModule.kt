package com.example.travel.di

import android.content.Context
import com.example.travel.repo.PropertyRepository
import com.example.travel.repo.PropertyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideListingRepository(
        context: Context
    ): PropertyRepository {
        return PropertyRepositoryImpl(
            context = context
        )
    }
}


