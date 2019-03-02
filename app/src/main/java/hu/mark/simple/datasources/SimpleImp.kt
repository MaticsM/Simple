package hu.mark.simple.datasources

import hu.mark.simple.data.SimpleResponses
import hu.mark.simple.interfaces.SimpleApi
import hu.mark.simple.network.SimpleRetrofitClient
import io.reactivex.Observable

class SimpleImp {

    private val githubApi: SimpleApi = SimpleRetrofitClient.retrofit.create(SimpleApi::class.java)

    fun getSimpleRepositories(): Observable<SimpleResponses>? {
        return Observable.fromCallable {
            val response = githubApi.getSimpleRepositories(
                "http://private-c60ade-guidebook1.apiary-mock.com/upcomingGuides",
                getSimpleCallQuery()
            ).execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                throw Throwable(response.message().toString())
            }
        }
    }

    private fun getSimpleCallQuery(): HashMap<String, String> {
        val result = HashMap<String, String>()
        return result
    }

}