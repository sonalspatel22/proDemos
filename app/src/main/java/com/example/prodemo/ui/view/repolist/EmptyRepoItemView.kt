package com.example.prodemo.ui.view.repolist

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prodemo.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.empty_item_view.view.*

class EmptyRepoItemView (context: Context) : ConstraintLayout(context) {

    private val startActivitySubject = PublishSubject.create<Boolean>()
    val startActivity: Observable<Boolean> = startActivitySubject.hide()

    init {
        LayoutInflater.from(context).inflate(R.layout.empty_item_view, this, true)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val padding8 = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.padding_16dp)
        setPadding(padding16, padding8, padding16, padding8)
    }

    fun bind() {
        add_button.setOnClickListener {
            startActivitySubject.onNext( true)
        }
    }
}