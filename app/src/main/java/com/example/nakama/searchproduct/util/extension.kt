package com.example.nakama.searchproduct.util

import com.example.nakama.searchproduct.model.data.ResultSearchDataModel
import com.example.nakama.searchproduct.model.data.StatusDataModel
import com.example.nakama.searchproduct.model.ui.DataItemUiModel
import com.example.nakama.searchproduct.model.ui.ResultSearchUiModel
import com.example.nakama.searchproduct.model.ui.StatusUiModel

val StatusDataModel.toStatusUiModel: StatusUiModel
    get() {
        val statusUiModel = StatusUiModel()

        val errorCode = errorCode
        statusUiModel.errorCode = errorCode

        var message = message
        statusUiModel.message = message

        return statusUiModel
    }

val ResultSearchDataModel.toSearchUiModel: ResultSearchUiModel
    get() {
        val resultUiModel = ResultSearchUiModel()

        val resultStatus = status
        resultUiModel.status = resultStatus.toStatusUiModel

        datas.let {
            val datas = it.map { dataSourceEntity ->

                return@map  DataItemUiModel(
                        itemId = dataSourceEntity.itemId,
                        itemName = dataSourceEntity.itemName,
                        itemUri = dataSourceEntity.itemUri,
                        itemImg = dataSourceEntity.itemImg,
                        itemPrice = dataSourceEntity.itemPrice,
                        itemShop = dataSourceEntity.shop
                )
            }
            resultUiModel.datas = datas
        }

        return resultUiModel
    }