package hu.mark.simple.util.recyclerview

import android.databinding.BaseObservable

interface IItemView {

    val layoutId: Int

    var vm : BaseObservable?

    fun bind(holder : BindingHolder)
}