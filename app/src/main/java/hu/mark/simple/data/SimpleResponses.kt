package hu.mark.simple.data

import com.google.gson.annotations.SerializedName

data class SimpleResponses(
    @SerializedName("data")
    val `data`: List<Response> = listOf(),
    @SerializedName("total")
    val total: String = ""
) {
    data class Response(
        @SerializedName("endDate")
        val endDate: String = "",
        @SerializedName("icon")
        val icon: String = "",
        @SerializedName("login_required")
        val loginRequired: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("objType")
        val objType: String = "",
        @SerializedName("startDate")
        val startDate: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("venue")
        val venue: Venue = Venue()
    ) {
        class Venue(
        )
    }
}

