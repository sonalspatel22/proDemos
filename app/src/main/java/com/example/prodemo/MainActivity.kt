package com.example.prodemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.prodemo.base.BaseDaggerActivity
import com.example.prodemo.base.autoDispose
import com.example.prodemo.ui.viewmodel.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseDaggerActivity() {

    private val mViewModel: MainViewModel by viewModels { modelFactory }
    val TAG = "ProDemoo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "Add Repo"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }

        add_button.setOnClickListener {
            mViewModel.getDirectory(owner_name.text.toString(), dir_name.text.toString())
        }

        mViewModel.error.observeOn(AndroidSchedulers.mainThread()).subscribe {
            it?.let {
                showToast(it)
            }
            Timber.tag(TAG).i("error" + it)
        }.autoDispose(compositeDisposable)

        mViewModel.directory.observe(this, Observer {
            it?.let {
                finish()
                Timber.tag(TAG).i("directory" + it)
            }
        })
    }

    companion object {
        const val DIRECTORY = "DIRECTORY"
        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.setNull()
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