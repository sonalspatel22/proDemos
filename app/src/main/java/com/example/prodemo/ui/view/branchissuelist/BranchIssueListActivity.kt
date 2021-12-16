package com.example.prodemo.ui.view.branchissuelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prodemo.R
import com.example.prodemo.base.BaseDaggerActivity
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.view.branchissuelist.viewmodel.BranchIssueViewModel
import kotlinx.android.synthetic.main.activity_branch_issue_list.*
import timber.log.Timber

class BranchIssueListActivity : BaseDaggerActivity() {

    var TAG = "ProDemoo"
    private lateinit var viewPagerAdapter: AgentPagerAdapter

    private val branchViewModel: BranchIssueViewModel by viewModels { modelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_issue_list)
        supportActionBar?.apply {
            title = "Details"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)

        }
        viewPagerAdapter = AgentPagerAdapter(supportFragmentManager)

        branchViewModel.getData(intent)
        branchViewModel.selectedRepoData.observe(this, Observer {
            it?.let {
                repoDescriptionTextView.text = it.description
                repoNameTextView.text = it.name
                Timber.tag(TAG).i("selectedRepoData==$it")
                viewPagerAdapter.add(BranchesFragment.newInstance(it), getString(R.string.str_branches))
                viewPagerAdapter.add(IssuesFragment.newInstance(it), getString(R.string.str_issues))
                tabLayout.setupWithViewPager(viewPager)
                viewPager.adapter = viewPagerAdapter
            }
        })
        branchViewModel.openInBrowser.observe(this, Observer {
            it?.let {
                openBrowser(it)
            }
        })
        branchViewModel.issueListSize.observe(this, Observer {
            it?.let {
                viewPagerAdapter.setTitle(it)
                Timber.tag(TAG).i("issueListSize==$it")
            }
        })
        branchViewModel.deleteistSize.observe(this, Observer {
            it?.let {
                val intent = Intent()
                intent.putExtra("DR", it)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })

    }

    companion object {
        const val DirectoryResponse = "DirectoryResponse"
        fun getIntent(context: Context, directoryResponse: DirectoryResponse): Intent {
            val intent = Intent(context, BranchIssueListActivity::class.java)
            intent.putExtra(DirectoryResponse, directoryResponse)
            return intent
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.deleteviewmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                branchViewModel.deleteSelectedRepo()
                finish()
            }
            R.id.viewInBrowser -> {
                branchViewModel.openInBrowser()

            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(Intent.createChooser(intent, "Browse with"))
    }

    override fun onDestroy() {
        super.onDestroy()
        branchViewModel.setNull()
    }

    fun setPageTitle(it: Int) {
        viewPagerAdapter.setTitle(it)
    }
}