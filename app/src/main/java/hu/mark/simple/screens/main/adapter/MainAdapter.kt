package hu.mark.simple.screens.main.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import hu.mark.simple.util.recyclerview.BindingHolder
import hu.mark.simple.util.recyclerview.IItemView

class MainAdapter : RecyclerView.Adapter<BindingHolder>() {

    var data: ArrayList<IItemView> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        return BindingHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        data.get(position).bind(holder)
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int {
        return data.get(position).layoutId
    }

    fun addData(list: ArrayList<IItemView>?) {
        if (list != null) {
            data.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        data = ArrayList()
        notifyDataSetChanged()
    }

}