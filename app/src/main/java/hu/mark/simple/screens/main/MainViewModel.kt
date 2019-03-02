package hu.mark.simple.screens.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.TextUtils
import android.util.Log
import hu.mark.simple.BR
import hu.mark.simple.data.SimpleCache
import hu.mark.simple.data.SimpleDatabase
import hu.mark.simple.data.SimpleResponses
import hu.mark.simple.datasources.SimpleImp
import hu.mark.simple.screens.main.items.MainItemView
import hu.mark.simple.screens.main.items.MainItemViewModel
import hu.mark.simple.util.recyclerview.IItemView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseObservable() {

    private val TAG = "MainViewModel"

    private var disposableSimple: Disposable? = null
    private var lastData: ArrayList<IItemView>? = null

    var dataList: ArrayList<IItemView>? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataList)
        }

    var noResult: Boolean = false
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.noResult)
        }

    var refreshing: Boolean = false
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshing)
        }

    fun getSimpleRepositories() {
        refreshing = true

        disposableSimple = SimpleImp()
            .getSimpleRepositories()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { gatherSimpleListData(it) },
                { t -> getError(t) }
            )
    }

    fun filterData(filter: String?) {
        if (TextUtils.isEmpty(filter)) {
            dataList = lastData
        } else if (lastData != null) {
            dataList = ArrayList()

            for (item: IItemView in lastData!!) {
                if ((item.vm as MainItemViewModel).data.name.toLowerCase().contains(filter!!.toLowerCase()))
                    dataList!!.add(item)
            }
        }
    }

    private fun gatherSimpleListData(it: SimpleResponses?) {
        Observable
            .fromCallable {
                val result = ArrayList<IItemView>()
                val caches = SimpleDatabase.database.daoSimple().fetchResponses()

                if (it != null) {
                    for (item: SimpleResponses.Response in it.data) {
                        var isFavourite = false

                        for (data: SimpleCache in caches) {
                            if (TextUtils.equals(data.name, item.name)) {
                                isFavourite = true
                                break
                            }
                        }

                        result.add(MainItemView(item, isFavourite))
                    }
                }

                dataList = result
                noResult = result.size == 0
                refreshing = false
                lastData = result
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getError(t: Throwable) {
        Log.d(TAG, t.localizedMessage)

        dataList = null
        refreshing = false
        noResult = true
        lastData = null
    }

    fun clearDisposable() {
        if (disposableSimple != null && !disposableSimple!!.isDisposed)
            disposableSimple!!.dispose()
    }

}