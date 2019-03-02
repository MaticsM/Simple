package hu.mark.simple.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import hu.mark.simple.R

class Util {

    companion object {
        fun isDeviceOnline(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun showImage(view: ImageView, url: String) {
            Glide
                .with(view.context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(view)
        }
    }

}