package com.example.nakama.searchproduct.model.data

import com.google.gson.annotations.SerializedName


data class ResultSearchDataModel (
        @SerializedName("status") val status: StatusDataModel,
        @SerializedName("data") val datas: List<DataResponseDataModel>)