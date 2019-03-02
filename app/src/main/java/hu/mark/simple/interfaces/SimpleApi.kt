package hu.mark.simple.interfaces

import hu.mark.simple.data.SimpleResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface SimpleApi {

    @GET
    fun getSimpleRepositories(@Url url: String, @QueryMap callQuery: HashMap<String, String>): Call<SimpleResponses>

}