package com.example.githubapp.di.module

import com.example.githubapp.ui.mainscreen.FragmentsBindingModule
import com.example.githubapp.ui.mainscreen.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
 abstract class MainActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentsBindingModule::class])
   internal abstract fun bindMainActivity(): MainActivity?
}