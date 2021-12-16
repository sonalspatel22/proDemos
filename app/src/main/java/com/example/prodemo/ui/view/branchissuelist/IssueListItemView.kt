package com.example.prodemo.ui.view.branchissuelist

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prodemo.R
import com.example.prodemo.base.loadImageCircle
import com.example.prodemo.ui.model.IssuesResponseItem
import kotlinx.android.synthetic.main.issue_list_item.view.*


class IssueListItemView(context: Context) : ConstraintLayout(context) {


    init {
        LayoutInflater.from(context).inflate(R.layout.issue_list_item, this, true)
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val padding8 = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.padding_16dp)
        setPadding(padding16, padding8, padding16, padding8)
    }


    fun bind(directory: IssuesResponseItem) {
        IssueDiscriptionTextView.text = directory.title
        creatorImageView.loadImageCircle(directory.user.avatarUrl)
        creatorNameTextView.text = directory.user.login
    }
}