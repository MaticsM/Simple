package hu.mark.simple.screens.main.items

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v7.app.AppCompatActivity
import android.view.View
import hu.mark.simple.BR
import hu.mark.simple.data.SimpleCache
import hu.mark.simple.data.SimpleDatabase
import hu.mark.simple.data.SimpleResponses
import hu.mark.simple.screens.main.info.MainItemInfoDialog
import hu.mark.simple.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainItemViewModel(val data: SimpleResponses.Response, isFavourite: Boolean) : BaseObservable() {

    var itemData: SimpleResponses.Response = data
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemData)
        }

    var favourite: Boolean = isFavourite
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.favourite)
        }

    fun onInfoClick(view: View) {
        val loc = IntArray(2)
        view.getLocationOnScreen(loc)

        MainItemInfoDialog().createDialogWith(data, loc)
            .show((view.context as AppCompatActivity).supportFragmentManager, Constants.INFO_DIALOG)
    }

    fun onFavouriteClick(view: View) {
        Observable
            .fromCallable {
                favourite = !favourite

                val cache = SimpleCache(
                    data.endDate,
                    data.icon,
                    data.loginRequired,
                    data.name,
                    data.objType,
                    data.startDate,
                    data.url
                )

                if (favourite) {
                    SimpleDatabase.database.daoSimple().insertOnlySingleResponse(cache)
                } else {
                    SimpleDatabase.database.daoSimple().deleteResponse(cache)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}