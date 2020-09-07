package com.example.githubapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.ui.repolist.RepoListViewModel
import com.example.githubapp.di.util.ViewModelKey
import com.example.githubapp.ui.factory.ViewModelFactory
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
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}