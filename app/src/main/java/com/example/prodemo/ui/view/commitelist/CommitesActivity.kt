package com.example.prodemo.ui.view.commitelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prodemo.R
import com.example.prodemo.base.BaseDaggerActivity
import com.example.prodemo.ui.model.CommiteRequestData
import kotlinx.android.synthetic.main.activity_commites.*
import timber.log.Timber

class CommitesActivity : BaseDaggerActivity() {
    val TAG = "ProDemoo"
    private val mViewModel: CommiteViewModel by viewModels { modelFactory }
    lateinit var commiteListAdapter: CommiteListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commites)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
        commiteListAdapter = CommiteListAdapter(this)
        commite_recyclerview.adapter =commiteListAdapter


        mViewModel.commiteList.observe(this, Observer {
            commiteListAdapter.commitesList = it
            Timber.tag(TAG).i("commiteList----"+it)
        })
        mViewModel.setTitle.observe(this, Observer {
           supportActionBar?.title ="Commites\n$it"
        })
        mViewModel.getData(intent)
    }

    companion object {
        const val COMMITEREQ ="COMMITEREQ"
        fun getIntent(context: Context, commiteRequestData: CommiteRequestData): Intent {
            val intent = Intent(context, CommitesActivity::class.java)
            intent.putExtra(COMMITEREQ,commiteRequestData)
            return intent
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}