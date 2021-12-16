package com.example.prodemo.ui.view.branchissuelist

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prodemo.R
import com.example.prodemo.ui.model.BranchesResponseItem
import com.example.prodemo.ui.model.DirectoryResponse
import com.example.prodemo.ui.model.IssuesResponseItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.branch_list_item.view.*

class ListItemView(context: Context) : ConstraintLayout(context) {



    private val onClickSubject: PublishSubject<BranchesResponseItem> = PublishSubject.create()
    val onClick: Observable<BranchesResponseItem> = onClickSubject.hide()

    init {
        LayoutInflater.from(context).inflate(R.layout.branch_list_item, this, true)
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val padding8 = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.padding_16dp)
        setPadding(padding16, padding8, padding16, padding8)
    }

    fun bind(branchesResponseItem: BranchesResponseItem) {
        ownerNameTextView.text = branchesResponseItem.name
        setOnClickListener {
            onClickSubject.onNext(branchesResponseItem)
        }

    }

}