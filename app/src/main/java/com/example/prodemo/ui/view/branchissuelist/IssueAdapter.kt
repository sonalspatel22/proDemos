package com.example.prodemo.ui.view.branchissuelist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.prodemo.ui.model.BranchesResponseItem
import com.example.prodemo.ui.model.IssuesResponseItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class IssueAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = mutableListOf<AdapterItem>()

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (isLoading) {
                adapterItems.clear()
                notifyDataSetChanged()
            }
        }

    init {
        isLoading = true
    }

    var repositoryList: MutableList<IssuesResponseItem> = mutableListOf()
        set(value) {
            field = value
            updateAdapterItems()
        }

    fun updateAdapterItems() {
        adapterItems.clear()
        if (repositoryList.size > 0) {
            repositoryList.forEach {
                adapterItems.add(AdapterItem.RepositoryItem(it))
            }
        } else {
            adapterItems.add(AdapterItem.EmptyItem())
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = adapterItems[position].type

    override fun getItemCount(): Int = adapterItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val adapterItem = adapterItems[position]) {
            is AdapterItem.RepositoryItem -> (holder.itemView as IssueListItemView).bind(adapterItem.issuesResponseItem)
            is AdapterItem.EmptyItem -> (holder.itemView as EmptyItemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TextItem -> {
                RepositoryHolder(IssueListItemView(context).apply { })
            }
            ViewType.EmptyItem -> {
                RepositoryHolder(EmptyItemView(context))
            }
        }
    }

    private class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view)

    private sealed class AdapterItem(val type: Int) {
        data class RepositoryItem(val issuesResponseItem: IssuesResponseItem) :
            AdapterItem(ViewType.TextItem.ordinal)

        class EmptyItem : AdapterItem(ViewType.EmptyItem.ordinal)
    }

    private enum class ViewType { TextItem, EmptyItem }

}