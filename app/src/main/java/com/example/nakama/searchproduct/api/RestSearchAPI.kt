package com.example.nakama.searchproduct.api

import com.example.nakama.searchproduct.model.data.ResultSearchDataModel
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RestSearchAPI {
    @GET("search/v1/product")
    fun getSearch(@Query("q") q: String, @Query("start") start: Int) : Deferred<ResultSearchDataModel?>
}