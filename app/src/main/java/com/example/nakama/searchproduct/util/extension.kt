package com.example.nakama.searchproduct.util

import com.example.nakama.searchproduct.model.data.ResultSearchDataModel
import com.example.nakama.searchproduct.model.ui.DataResponseUiModel
import com.example.nakama.searchproduct.model.ui.ResultSearchUiModel

val ResultSearchDataModel.toSearchUiModel: ResultSearchUiModel
    get() {
        val resultUiModel = ResultSearchUiModel()

        val resultStatus = status
        resultUiModel.status = resultStatus

        datas.let {
            val datas = it.map { dataSourceEntity ->

                return@map  DataResponseUiModel(
                        id = dataSourceEntity.id,
                        name = dataSourceEntity.name,
                        uri = dataSourceEntity.uri,
                        imgUri = dataSourceEntity.imageUri,
                        price = dataSourceEntity.price,
                        shop = dataSourceEntity.shop
                )
            }
            resultUiModel.datas = datas
        }

        return resultUiModel
    }