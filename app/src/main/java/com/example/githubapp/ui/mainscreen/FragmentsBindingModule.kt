package com.example.githubapp.ui.mainscreen

import com.example.githubapp.ui.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector
    abstract fun provideRepoListFragment(): RepoListFragment?
}