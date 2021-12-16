package com.example.prodemo.di

import com.example.prodemo.MainActivity
import com.example.prodemo.ui.view.AddRepositoryActivity
import com.example.prodemo.ui.view.branchissuelist.BranchIssueListActivity
import com.example.prodemo.ui.view.branchissuelist.BranchesFragment
import com.example.prodemo.ui.view.branchissuelist.IssuesFragment
import com.example.prodemo.ui.view.commitelist.CommitesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector
    internal abstract fun contributeCommitesActivity(): CommitesActivity
    @ContributesAndroidInjector
    internal abstract fun contributeBranchIssueListActivity(): BranchIssueListActivity
    @ContributesAndroidInjector
    internal abstract fun contributeBranchesFragment(): BranchesFragment
    @ContributesAndroidInjector
    internal abstract fun contributeIssuesFragment(): IssuesFragment
    @ContributesAndroidInjector
    internal abstract fun contributeAddRepositoryActivity(): AddRepositoryActivity

}