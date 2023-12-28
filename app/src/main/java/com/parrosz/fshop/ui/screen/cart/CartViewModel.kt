package com.parrosz.fshop.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parrosz.fshop.data.FShopRepository
import com.parrosz.fshop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: FShopRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderFShop() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderFShop()
                .collect { orderFShop ->
                    val totalHarga =
                        orderFShop.sumOf { it.fshop.harga * it.count }
                    _uiState.value = UiState.Success(CartState(orderFShop, totalHarga))
                }
        }
    }

    fun updateOrderFShop(fshopId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFShop(fshopId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderFShop()
                    }
                }
        }
    }
}