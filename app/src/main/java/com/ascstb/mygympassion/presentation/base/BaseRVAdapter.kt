package com.ascstb.mygympassion.presentation.base

import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ascstb.mygympassion.BR
import timber.log.Timber

abstract class BaseRVAdapter<IT : Parcelable, IVM : BaseViewModel, VDB : ViewDataBinding>(
    private val listener: (View, IT) -> Unit
) : RecyclerView.Adapter<BaseRVAdapter<IT, IVM, VDB>.ViewHolder>() {
    lateinit var viewModel: IVM
    private lateinit var binding: VDB
    private var originalBackground: Drawable? = null

    var itemList: List<IVM> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val itemBinding: VDB) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: IT, clickListener: (View, IT) -> Unit) {
            itemView.setOnClickListener { clickListener(itemView, item) }
        }
    }

    protected abstract fun inflateDataBinding(inflater: LayoutInflater, container: ViewGroup?): VDB
    protected abstract fun getBindItem(itemViewModel: IVM): IT?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        inflateDataBinding(LayoutInflater.from(parent.context), parent).run {
            binding = this

            ViewHolder(binding)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.setVariable(BR.viewModel, itemList[position])
        getBindItem(itemList[position])?.let {
            holder.bind(it, listener)
        }
        holder.itemBinding.executePendingBindings()
    }

    override fun getItemCount(): Int = itemList.size

    fun clear() {
        Timber.d("BaseRVAdapter_TAG: onClear: ")
        itemList = emptyList()
        notifyDataSetChanged()
    }
}