package com.example.todaysweatherforecast

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.todaysweatherforecast.di.AppDiComponent
import com.example.todaysweatherforecast.di.DaggerAppDiComponent
import com.example.todaysweatherforecast.di.WeatherActivityModule
import com.example.todaysweatherforecast.retrofit.Api
import retrofit2.Retrofit
import java.io.IOException


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory




class App : Application() {

    lateinit var injector: AppDiComponent
        private set

    override fun onCreate() {
        super.onCreate()


        INSTANCE = this
        injector = DaggerAppDiComponent.builder()
            .weatherActivityModule(WeatherActivityModule(this))
            .build()
    }


    @Throws(IOException::class)
    fun connectServer() : Api? {




        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apixu.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getOkHttClient())
            .build()

        return retrofit.create(Api::class.java)

    }

    val cookie = "Hello"

    internal inner class cookieInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            if (cookie != null)
                request = request.newBuilder()
                    .removeHeader("Cookie")
                    .addHeader("Cookie", cookie)
                    .build()
            else
                request = request.newBuilder().build()
            return chain.proceed(request)
        }
    }


    private fun getOkHttClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(cookieInterceptor())
        builder.addInterceptor(Interceptor { chain ->




            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header("Authorization", String.format("Bearer %s", "name"))
                .method(original.method(), original.body())

            val request = requestBuilder.build()


            println(requestBuilder.build())
            println(request)
            println(chain.proceed(request))

            chain.proceed(request)
        })
        return builder.build()
    }

    companion object {
        private lateinit var INSTANCE: App
        @JvmStatic
        fun get(): App = INSTANCE
    }
}