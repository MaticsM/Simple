package hu.mark.simple.util

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import hu.mark.simple.screens.main.adapter.MainAdapter
import hu.mark.simple.util.recyclerview.IItemView

@BindingAdapter("bind:setDataList")
fun setDataList(view: RecyclerView, list: ArrayList<IItemView>?) {
    (view.adapter as MainAdapter).clear()
    (view.adapter as MainAdapter).addData(list)
}

@BindingAdapter("bind:showImageFromUrl")
fun showImageFromUrl(view: ImageView, url: String) {
    Util.showImage(view, url)
}

@BindingAdapter("bind:setVisibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:setRefreshing")
fun setRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}

@BindingAdapter("bind:setFavourite")
fun setFavourite(view: ImageView, favourite: Boolean) {
    if (favourite) {
        view.setImageResource(android.R.drawable.star_on)
    } else {
        view.setImageResource(android.R.drawable.star_off)
    }
}