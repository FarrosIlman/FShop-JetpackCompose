package com.parrosz.fshop.ui.screen.cart

import com.parrosz.fshop.model.OrderFShop

data class CartState(
    val orderFShop: List<OrderFShop>,
    val totalHarga: Int
)