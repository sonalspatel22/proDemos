package com.example.prodemo.ui.view.branchissuelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prodemo.R
import com.example.prodemo.base.BaseDaggerFragment
import com.example.prodemo.ui.model.BranchesResponseItem
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.view.branchissuelist.viewmodel.BranchIssueViewModel
import com.example.prodemo.ui.view.branchissuelist.viewmodel.BranchViewModel
import com.example.prodemo.ui.view.commitelist.CommitesActivity
import kotlinx.android.synthetic.main.fragment_branches.*
import timber.log.Timber


class BranchesFragment : BaseDaggerFragment() {
    val TAG = "BranchesFragment"

    companion object {
        const val RECENT_SEARCH = "RecentSearch"
        fun newInstance(directoryResponse: DirectoryResponse) : BranchesFragment{
            val fragment = BranchesFragment()
            val bundle = Bundle()
            bundle.putSerializable(RECENT_SEARCH, directoryResponse)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var branchAdapter: BranchAdapter

    private val mViewModel: BranchViewModel by viewModels { modelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_branches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dividerItemDecoration =
            DividerItemDecoration(baseActivity, LinearLayoutManager.VERTICAL)
        branch_recyclerview.addItemDecoration(dividerItemDecoration)
        branch_recyclerview.adapter = branchAdapter
        branchAdapter.apply {
            onclick.observe(viewLifecycleOwner, Observer<BranchesResponseItem> {
                it?.let {
                    mViewModel.startCommitActivity(it)
                    Timber.tag(TAG).i("" + it)
                }
            })
        }

        mViewModel.branchList.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.tag(TAG).i("" + it)
                branchAdapter.repositoryList = it
            }
        })

        mViewModel.startCommite.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.tag(TAG).i("startActivity(CommitesActivity" + it)
                startActivity(CommitesActivity.getIntent(baseActivity, it))
            }
        })

        mViewModel.getBranches(arguments)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        branchAdapter = BranchAdapter(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
