package com.parrosz.fshop.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parrosz.fshop.data.FShopRepository
import com.parrosz.fshop.model.FShop
import com.parrosz.fshop.model.OrderFShop
import com.parrosz.fshop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFShopViewModel(private val repository: FShopRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderFShop>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderFShop>>
        get() = _uiState

    fun getFshopById(fshopId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFShopById(fshopId))
        }
    }

    fun addToCart(fshop: FShop, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFShop(fshop.id, count)
        }
    }
}