package com.example.githubapp

import com.example.githubapp.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/*

    Entry class for the appliction.
    Build Dependency module
 */
class GithubApp : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val applicationComponent = DaggerApplicationComponent.builder().application(this)!!.build()
        applicationComponent.inject(this)
        return applicationComponent
    }


}