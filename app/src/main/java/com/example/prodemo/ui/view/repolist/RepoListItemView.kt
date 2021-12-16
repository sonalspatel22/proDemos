package com.example.prodemo.ui.view.repolist

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prodemo.R
import com.example.prodemo.ui.model.DirectoryResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.repo_list_item_view.view.*

class RepoListItemView(context: Context) : ConstraintLayout(context) {

    private val shareSubject: PublishSubject<DirectoryResponse> = PublishSubject.create()
    val share: Observable<DirectoryResponse> = shareSubject.hide()

    private val onClickSubject: PublishSubject<DirectoryResponse> = PublishSubject.create()
    val onClick: Observable<DirectoryResponse> = onClickSubject.hide()

    init {
        LayoutInflater.from(context).inflate(R.layout.repo_list_item_view, this, true)
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val padding8 = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.padding_16dp)
        setPadding(padding16, padding8, padding16, padding8)
    }

    fun bind(directory: DirectoryResponse) {
        ownerNameTextView.text = directory.name
        repositoryDescriptionTextView.text = directory.description
        shareTextView.setOnClickListener { shareSubject.onNext(directory) }
        setOnClickListener {
            onClickSubject.onNext(directory)
        }
    }

}