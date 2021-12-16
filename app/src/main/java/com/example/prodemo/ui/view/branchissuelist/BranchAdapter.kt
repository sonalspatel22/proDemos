package com.example.prodemo.ui.view.branchissuelist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.prodemo.ui.model.BranchesResponseItem
import io.reactivex.android.schedulers.AndroidSchedulers


class BranchAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterItems = mutableListOf<AdapterItem>()
    val onclick = MutableLiveData<BranchesResponseItem>()

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


    var repositoryList: MutableList<BranchesResponseItem> = mutableListOf()
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
            is AdapterItem.RepositoryItem -> (holder.itemView as ListItemView).bind(adapterItem.branchesResponseItem)
            is AdapterItem.EmptyItem -> (holder.itemView as EmptyItemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TextItem -> {
                RepositoryHolder(ListItemView(context).apply {
//                    share.observeOn(AndroidSchedulers.mainThread()).subscribe {
//                        shareUrl.value = it
//                    }
                    onClick.observeOn(AndroidSchedulers.mainThread()).subscribe {
                        onclick.value = it
                    }
                })
            }
            ViewType.EmptyItem -> { RepositoryHolder(EmptyItemView(context)) }
        }
    }

    private class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view)

    private sealed class AdapterItem(val type: Int) {
        data class RepositoryItem(val branchesResponseItem: BranchesResponseItem) : AdapterItem(ViewType.TextItem.ordinal)
        class EmptyItem : AdapterItem(ViewType.EmptyItem.ordinal)
    }

    private enum class ViewType { TextItem, EmptyItem }

}