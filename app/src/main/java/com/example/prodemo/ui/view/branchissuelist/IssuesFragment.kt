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
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.view.branchissuelist.viewmodel.IssueViewModel

import kotlinx.android.synthetic.main.fragment_issues.*
import timber.log.Timber

class IssuesFragment : BaseDaggerFragment() {

    companion object {
        const val RECENT_SEARCH = "RecentSearch"
        fun newInstance(directoryResponse: DirectoryResponse) : IssuesFragment{
            val fragment = IssuesFragment()
            val bundle = Bundle()
            bundle.putSerializable(RECENT_SEARCH, directoryResponse)
            fragment.arguments = bundle
            return fragment
        }
    }
    val TAG ="IssuesFragment"
    private lateinit var issueAdapter: IssueAdapter

    private val mViewModel: IssueViewModel by viewModels { modelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_issues, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        issueAdapter = IssueAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dividerItemDecoration = DividerItemDecoration(baseActivity, LinearLayoutManager.VERTICAL)
        issues_recyclerview.addItemDecoration(dividerItemDecoration)
        issues_recyclerview.adapter = issueAdapter
        mViewModel.issueList.observe(viewLifecycleOwner, Observer {
            it?.let {
                issueAdapter.repositoryList = it
                Timber.tag(TAG).i("" + it)
            }
        })
        mViewModel.issueListSize.observe(viewLifecycleOwner, Observer {
            it?.let {
//                (activity as BranchIssueListActivity).setPageTitle(it)
            }
        })
        mViewModel.getIssues(arguments)

    }



}
