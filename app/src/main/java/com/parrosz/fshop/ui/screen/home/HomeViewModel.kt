package com.parrosz.fshop.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parrosz.fshop.data.FShopRepository
import com.parrosz.fshop.model.OrderFShop
import com.parrosz.fshop.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: FShopRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderFShop>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderFShop>>>
        get() = _uiState

    fun getAllFShop() {
        viewModelScope.launch {
            repository.getAllFShop()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderFshop ->
                    _uiState.value = UiState.Success(orderFshop)
                }
        }
    }
}