package hu.mark.simple.network

import hu.mark.simple.MainApplication
import hu.mark.simple.util.Util
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

object SimpleOkHttpClient {

    var okHttpClient: OkHttpClient

    init {

        val cacheFile = File(MainApplication.applicationContext().cacheDir, "HttpCache")
        cacheFile.mkdirs()

        val cache = Cache(cacheFile, 10 * 1024 * 1024)

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (Util.isDeviceOnline(MainApplication.applicationContext()))
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .cache(cache)
            .build()
    }

}