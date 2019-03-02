package hu.mark.simple.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity
data class SimpleCache(
    @SerializedName("endDate")
    var endDate: String? = "",
    @SerializedName("icon")
    var icon: String? = "",
    @SerializedName("login_required")
    var loginRequired: Int = 0,
    @NonNull
    @PrimaryKey
    @SerializedName("name")
    var name: String = "",
    @SerializedName("objType")
    var objType: String? = "",
    @SerializedName("startDate")
    var startDate: String? = "",
    @SerializedName("url")
    var url: String? = ""
)
