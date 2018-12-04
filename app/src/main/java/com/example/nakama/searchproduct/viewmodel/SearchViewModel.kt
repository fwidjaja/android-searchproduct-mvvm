package com.example.nakama.searchproduct.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.nakama.searchproduct.model.data.ResultSearchDataModel
import com.example.nakama.searchproduct.model.data.StatusDataModel
import com.example.nakama.searchproduct.util.toSearchUiModel
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext

class SearchViewModel(val sourceId: String, val api: RestSearchAPI, val mainThread: CoroutineContext, val bgThread: CoroutineContext) : ViewModel() {

    private val data = MutableLiveData<ResultSearchDataModel>()

    var uiData = Transformations.map(data) {
        it.toSearchUiModel
    }!!

    val loading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = Transformations.map(loading) {
        it
    }

    val error = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = Transformations.map(error) {
        it
    }

    fun update() {
        GlobalScope.launch(mainThread) {
            loading.value = true
            try {
                data.value = withContext(bgThread) { api.getListHeadlines(sourceId, Constants.KEY_API).await() }
            } catch (err: Exception) {
                error.value = true
                println("++ ERROR! ${err.localizedMessage}")
            }
            loading.value = false
        }
    }

    fun search(q: String?) {
        GlobalScope.launch(mainThread) {
            loading.value = true
            try {
                data.value = withContext(bgThread) { api.getSearch(q!!, Constants.KEY_API).await() }
            } catch (err: Exception) {
                error.value = true
                println("++ ERROR! ${err.localizedMessage}")
            }
            loading.value = false
        }
    }
}