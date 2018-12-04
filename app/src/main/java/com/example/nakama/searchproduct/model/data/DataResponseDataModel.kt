package com.example.nakama.searchproduct.model.data

import com.google.gson.annotations.SerializedName

data class DataResponseDataModel (@SerializedName("id") val id: Long = 0L,
                                  @SerializedName("name") val name: String = "",
                                  @SerializedName("uri") val uri: String = "",
                                  @SerializedName("image_uri") val imageUri: String = "",
                                  @SerializedName("price") val price: String = "",
                                  @SerializedName("shop") val shop: ShopDataModel)