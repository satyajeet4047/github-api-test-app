package com.example.githubapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.ui.repolist.RepoListViewModel
import com.example.githubapp.di.util.ViewModelKey
import com.example.githubapp.ui.contributordetail.ContributorDetailViewModel
import com.example.githubapp.ui.factory.ViewModelFactory
import com.example.githubapp.ui.repodetail.RepoDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ContextModule::class])
abstract class ViewModelFactoryModule {


    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    internal abstract fun bindRepoListViewModel(repoListViewModel: RepoListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailViewModel::class)
    internal abstract fun bindRepoDetailViewModel(repoDetailViewModel: RepoDetailViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContributorDetailViewModel::class)
    internal abstract fun bindContributorDetailViewModel(contributorDetailViewModel: ContributorDetailViewModel) : ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}