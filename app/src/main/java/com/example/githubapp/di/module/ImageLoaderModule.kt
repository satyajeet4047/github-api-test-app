package com.example.githubapp.di.module

import com.example.githubapp.util.ImageLoaderUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryModule::class])
class ImageLoaderModule {

    @Singleton
    @Provides
    fun provideImageLoaderUtil () : ImageLoaderUtil{
        return ImageLoaderUtil()
    }
}