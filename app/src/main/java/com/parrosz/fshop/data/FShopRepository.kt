package com.parrosz.fshop.data

import com.parrosz.fshop.model.FakeShopDataSource
import com.parrosz.fshop.model.OrderFShop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FShopRepository {
    private val orderFShop = mutableListOf<OrderFShop>()

    init {
        if (orderFShop.isEmpty()) {
            FakeShopDataSource.dummyShop.forEach {
                orderFShop.add(OrderFShop(it, 0))
            }
        }
    }

    fun getAllFShop(): Flow<List<OrderFShop>> {
        return flowOf(orderFShop)
    }

    fun getOrderFShopById(shopId: Long): OrderFShop {
        return orderFShop.first {
            it.fshop.id == shopId
        }
    }

    fun updateOrderFShop(shopId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderFShop.indexOfFirst { it.fshop.id == shopId }
        val result = if (index >= 0) {
            val orderShop = orderFShop[index]
            orderFShop[index] =
                orderShop.copy(fshop = orderShop.fshop, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderFShop(): Flow<List<OrderFShop>> {
        return getAllFShop()
            .map { orderShops ->
                orderShops.filter { orderShop ->
                    orderShop.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: FShopRepository? = null

        fun getInstance(): FShopRepository =
            instance ?: synchronized(this) {
                FShopRepository().apply {
                    instance = this
                }
            }
    }
}