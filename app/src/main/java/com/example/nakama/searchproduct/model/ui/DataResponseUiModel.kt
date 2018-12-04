package com.example.nakama.searchproduct.model.ui

import com.example.nakama.searchproduct.model.data.ShopDataModel

class DataResponseUiModel (
    var id: Long? = null,
    var name: String? = null,
    var uri: String? = null,
    var imgUri: String? = null,
    var price: String? = null,
    var shop: ShopDataModel? = null)