package com.example.prodemo.ui.view.commitelist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prodemo.ui.model.commiteResponseItem
import com.example.prodemo.ui.view.repolist.EmptyRepoItemView

class CommiteListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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


    var commitesList: MutableList<commiteResponseItem> = mutableListOf()
        set(value) {
            field = value
            updateAdapterItems()
        }

    fun updateAdapterItems() {
        adapterItems.clear()
        if (commitesList.size > 0) {
            commitesList.forEach {
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
            is AdapterItem.RepositoryItem -> (holder.itemView as CommiteListItemView).bind(adapterItem.directoryResponse)
            is AdapterItem.EmptyItem -> (holder.itemView as EmptyRepoItemView).bind().apply {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TextItem -> {
                RepositoryHolder(CommiteListItemView(context))
            }
            ViewType.EmptyItem -> {
                RepositoryHolder(EmptyRepoItemView(context))
            }
        }
    }

    private class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view)

    private sealed class AdapterItem(val type: Int) {
        data class RepositoryItem(val directoryResponse: commiteResponseItem) : AdapterItem(ViewType.TextItem.ordinal)
        class EmptyItem : AdapterItem(ViewType.EmptyItem.ordinal)
    }

    private enum class ViewType { TextItem, EmptyItem }

}