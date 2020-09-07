package com.example.githubapp.di.component

import android.app.Application
import com.example.githubapp.GithubApp
import com.example.githubapp.di.module.ContextModule
import com.example.githubapp.di.module.MainActivityBindingModule
import com.example.githubapp.di.module.NetworkModule
import com.example.githubapp.di.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/*
    Main Application component class to inject different modules
 */
@Singleton
@Component(modules = [ContextModule::class, AndroidSupportInjectionModule::class,  MainActivityBindingModule::class,
    ViewModelFactoryModule::class,NetworkModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication>{

    fun inject(githubApp: GithubApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        fun build(): ApplicationComponent
    }
}