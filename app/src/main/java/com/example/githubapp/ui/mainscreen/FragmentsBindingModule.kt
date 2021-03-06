package com.example.githubapp.ui.mainscreen

import com.example.githubapp.ui.contributordetail.ContributorDetailFragment
import com.example.githubapp.ui.repodetail.RepoDetailFragment
import com.example.githubapp.ui.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector
    abstract fun provideRepoListFragment(): RepoListFragment?

    @ContributesAndroidInjector
    abstract fun provideRepoDetailFragment(): RepoDetailFragment?

    @ContributesAndroidInjector
    abstract fun provideContributorDetailFragment(): ContributorDetailFragment?
}