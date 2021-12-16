package com.example.prodemo.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prodemo.MainActivity
import com.example.prodemo.R
import com.example.prodemo.ui.view.repolist.RepoListAdapter
import com.example.prodemo.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_add_repository2.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prodemo.base.BaseDaggerActivity
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.view.branchissuelist.BranchIssueListActivity
import timber.log.Timber


class AddRepositoryActivity : BaseDaggerActivity() {

    var TAG = "ProDemoo"
    lateinit var repoListAdapter: RepoListAdapter
    private val mViewModel: MainViewModel by viewModels { modelFactory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_repository2)
        supportActionBar?.apply {
            title = "Github Browser"
            setDisplayShowTitleEnabled(true)

        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        repo_recyclerView.addItemDecoration(dividerItemDecoration)
        repoListAdapter = RepoListAdapter(this).apply {
            startRepoActivity.observe(this@AddRepositoryActivity, Observer {
                startAddRepoActivity()
                Timber.tag(TAG).i("startRepoActivity$it")
            })
            shareUrl.observe(this@AddRepositoryActivity, Observer {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it.url)
                    type = "text/plain"

                }
                startActivity(Intent.createChooser(sendIntent, "Share options"))
                Timber.tag(TAG).i("shareUrl$it")
            })
            onclick.observe(this@AddRepositoryActivity, Observer {
                startActivityForResult(BranchIssueListActivity.getIntent(this@AddRepositoryActivity, it),1111)
                Timber.tag(TAG).i("onclick$it")
            })

        }
        repo_recyclerView.adapter = repoListAdapter

        mViewModel.directoryList.observe(this@AddRepositoryActivity, Observer {
            repoListAdapter.repositoryList = it
            Timber.tag(TAG).i("directoryLists$it")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> startAddRepoActivity()
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun startAddRepoActivity() {
        startActivity(MainActivity.getIntent(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1111&&resultCode==Activity.RESULT_OK){
            val dr= data?.getSerializableExtra("DR") as DirectoryResponse
            mViewModel.deleteSelectedRepo(dr)
        }
    }
}