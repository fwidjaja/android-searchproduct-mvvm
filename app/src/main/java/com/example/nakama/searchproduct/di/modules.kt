package com.fwidjaja.newsaggregator.di

import com.example.nakama.searchproduct.BuildConfig
import com.example.nakama.searchproduct.api.RestSearchAPI
import com.example.nakama.searchproduct.viewmodel.SearchViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.Main
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.experimental.CoroutineContext

val searchModules = module {
    single<CoroutineContext> (name = "mainThread") {
        kotlinx.coroutines.experimental.Dispatchers.Main
    }

    single<CoroutineContext> (name = "bgThread") {
        Dispatchers.IO
    }

    single<RestSearchAPI> (name = "restApi") {

        val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()

        Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ace.tokopedia.com")
                .client(okHttpClient)
                .build().create(RestSearchAPI::class.java)
    }

    viewModel {
        SearchViewModel(api = get(name = "restApi"), mainThread = get(name = "mainThread"), bgThread = get(name = "bgThread"))
    }
}