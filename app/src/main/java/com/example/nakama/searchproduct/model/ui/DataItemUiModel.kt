package com.example.nakama.searchproduct.model.ui

import com.example.nakama.searchproduct.model.data.ShopDataModel

class DataItemUiModel (
        var itemId: Long? = null,
        var itemName: String? = null,
        var itemUri: String? = null,
        var itemImg: String? = null,
        var itemPrice: String? = null,
        var itemShop: ShopDataModel? = null)