package com.example.prodemo.ui.view.repolist

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.prodemo.ui.model.DirectoryResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class RepoListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val adapterItems = mutableListOf<AdapterItem>()
    val startRepoActivity = MutableLiveData<Boolean>()
    val onclick = MutableLiveData<DirectoryResponse>()
    val shareUrl = MutableLiveData<DirectoryResponse>()
    private val shareAgentSubject: PublishSubject<String> = PublishSubject.create()
    val shareAgent: Observable<String> = shareAgentSubject.hide()
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


    var repositoryList: MutableList<DirectoryResponse> = mutableListOf()
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
            is AdapterItem.RepositoryItem -> (holder.itemView as RepoListItemView).bind(adapterItem.directoryResponse)
            is AdapterItem.EmptyItem -> (holder.itemView as EmptyRepoItemView).bind().apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TextItem -> {
                RepositoryHolder(RepoListItemView(context).apply {
                    share.observeOn(AndroidSchedulers.mainThread()).subscribe {
                        shareUrl.value = it
                    }
                    onClick.observeOn(AndroidSchedulers.mainThread()).subscribe {
                        onclick.value = it
                    }
                })
            }
            ViewType.EmptyItem -> {
                RepositoryHolder(EmptyRepoItemView(context).apply {
                    startActivity.observeOn(AndroidSchedulers.mainThread()).subscribe {
                        startRepoActivity.value = it
                    }
                })
            }
        }
    }

    private class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view)

    private sealed class AdapterItem(val type: Int) {
        data class RepositoryItem(val directoryResponse: DirectoryResponse) :
            AdapterItem(ViewType.TextItem.ordinal)

        class EmptyItem : AdapterItem(ViewType.EmptyItem.ordinal)
    }

    private enum class ViewType { TextItem, EmptyItem }

}