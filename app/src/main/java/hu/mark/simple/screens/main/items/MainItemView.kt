package hu.mark.simple.screens.main.items

import android.databinding.BaseObservable
import hu.mark.simple.BR
import hu.mark.simple.R
import hu.mark.simple.data.SimpleResponses
import hu.mark.simple.util.recyclerview.BindingHolder
import hu.mark.simple.util.recyclerview.IItemView

class MainItemView(private val item: SimpleResponses.Response, private val isFavourite: Boolean) : IItemView {

    override val layoutId: Int
        get() = R.layout.main_item

    override var vm: BaseObservable? = null
        get() {
            if (field == null) {
                field = MainItemViewModel(item, isFavourite)
            }
            return field
        }

    override fun bind(holder: BindingHolder) {
        holder.binding.setVariable(BR.data, vm)
    }
}