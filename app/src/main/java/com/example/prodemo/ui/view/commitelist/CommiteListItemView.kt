package com.example.prodemo.ui.view.commitelist

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.prodemo.R
import com.example.prodemo.base.loadImageCircle
import com.example.prodemo.ui.model.commiteResponseItem
import kotlinx.android.synthetic.main.commite_list_item_view.view.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CommiteListItemView(context: Context) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.commite_list_item_view, this, true)
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val padding8 = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val padding16 = context.resources.getDimensionPixelSize(R.dimen.padding_16dp)
        setPadding(padding16, padding8, padding16, padding8)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(directory: commiteResponseItem) {
        CreatedAtTextView.text =directory.commit.committer.date
        CommiteMessageTextView.text = directory.commit.message
        creatorImageView.loadImageCircle(directory.committer.avatarUrl)
        creatorNameTextView.text = directory.committer.login
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates(date: String): String {
        val d: ZonedDateTime = ZonedDateTime.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        val dates = d.format(formatter)
        return dates
    }
}