package com.parrosz.fshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parrosz.fshop.data.FShopRepository
import com.parrosz.fshop.ui.screen.cart.CartViewModel
import com.parrosz.fshop.ui.screen.detail.DetailFShopViewModel
import com.parrosz.fshop.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FShopRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailFShopViewModel::class.java)) {
            return DetailFShopViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}