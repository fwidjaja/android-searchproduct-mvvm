package com.example.nakama.searchproduct.model.data

import com.google.gson.annotations.SerializedName

data class DataResponseDataModel (@SerializedName("id") val itemId: Long = 0L,
                                  @SerializedName("name") val itemName: String = "",
                                  @SerializedName("uri") val itemUri: String = "",
                                  @SerializedName("image_uri") val itemImg: String = "",
                                  @SerializedName("price") val itemPrice: String = "",
                                  @SerializedName("shop") val shop: ShopDataModel)