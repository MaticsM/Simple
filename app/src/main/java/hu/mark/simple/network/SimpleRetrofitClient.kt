package hu.mark.simple.network

import hu.mark.simple.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SimpleRetrofitClient {

    val retrofit: Retrofit = Retrofit.Builder()
        .client(SimpleOkHttpClient.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

}