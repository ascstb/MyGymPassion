package com.ascstb.mygympassion.presentation.base

import android.util.SparseArray
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), Observable {
    private val jobs = mutableListOf<Job>()

    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    private val onChangeObservers = SparseArray<MutableList<() -> Unit>>()

    var launchInBackground: (suspend CoroutineScope.() -> Unit) -> Job = { block ->
        GlobalScope.launch(Dispatchers.Main) { block(this) }
    }

    fun background(block: suspend CoroutineScope.() -> Unit) {
        jobs.add(launchInBackground(block))
    }

    init {
        this.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (onChangeObservers[propertyId] != null) {
                    onChangeObservers[propertyId].forEach { it.invoke() }
                }
            }
        })
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks?.add(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, fieldId, null)
    }

    fun onPropertyChanged(propertyId: Int, action: () -> Unit) {
        if (onChangeObservers[propertyId] != null) {
            onChangeObservers[propertyId].add(action)
        } else {
            val observers = ArrayList<() -> Unit>()
            observers.add(action)
            onChangeObservers.put(propertyId, observers)
        }
    }
}