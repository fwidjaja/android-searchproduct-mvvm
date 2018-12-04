package com.example.nakama.searchproduct.model.data

import com.google.gson.annotations.SerializedName

data class StatusDataModel (@SerializedName("error_code") val errorCode: Int = 0,
                       @SerializedName("message") val message: String = "")