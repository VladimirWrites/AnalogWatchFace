package com.vlad1m1r.watchface.data.di

import android.content.Context
import android.content.SharedPreferences
import com.vlad1m1r.watchface.data.ColorStorage
import com.vlad1m1r.watchface.data.DataStorage
import com.vlad1m1r.watchface.data.KEY_ANALOG_WATCH_FACE
import com.vlad1m1r.watchface.data.SizeStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            KEY_ANALOG_WATCH_FACE,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideDataStorage(
        sharedPreferences: SharedPreferences
    ): DataStorage {
        return DataStorage(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideColorStorage(
        @ApplicationContext appContext: Context,
        sharedPreferences: SharedPreferences
    ): ColorStorage {
        return ColorStorage(appContext, sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideSizeStorage(
        @ApplicationContext appContext: Context,
        sharedPreferences: SharedPreferences
    ): SizeStorage {
        return SizeStorage(appContext, sharedPreferences)
    }
}