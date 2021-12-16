package com.sabrewinginfotech.reesguru.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prodemo.di.ViewModelKey
import com.example.prodemo.ui.view.branchissuelist.viewmodel.BranchIssueViewModel
import com.example.prodemo.ui.view.branchissuelist.viewmodel.BranchViewModel
import com.example.prodemo.ui.view.branchissuelist.viewmodel.IssueViewModel
import com.example.prodemo.ui.view.commitelist.CommiteViewModel
import com.example.prodemo.ui.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun postLoginViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommiteViewModel::class)
    internal abstract fun postCommiteViewModel(viewModel: CommiteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BranchViewModel::class)
    internal abstract fun postBranchViewModel(viewModel: BranchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IssueViewModel::class)
    internal abstract fun postIssueViewModel(viewModel: IssueViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(BranchIssueViewModel::class)
    internal abstract fun postBranchIssueViewModel(viewModel: BranchIssueViewModel): ViewModel
}