package com.example.nakama.searchproduct.model.data

import com.google.gson.annotations.SerializedName

data class ShopDataModel (@SerializedName("id") val id: Long = 0L,
                     @SerializedName("name") val name: String = "",
                     @SerializedName("uri") val uri: String = "",
                     @SerializedName("is_gold") val isGold: Int = 0,
                     @SerializedName("rating") val rating: Int = 0,
                     @SerializedName("location") val location: String = "",
                     @SerializedName("reputation_image_uri") val reputationImageUri: String = "",
                     @SerializedName("shop_lucky") val shopLucky: String = "",
                     @SerializedName("city") val city: String = "")