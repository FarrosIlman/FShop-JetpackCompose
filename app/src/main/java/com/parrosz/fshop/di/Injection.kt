package com.parrosz.fshop.di

import com.parrosz.fshop.data.FShopRepository

object Injection {
    fun provideRepository(): FShopRepository {
        return FShopRepository.getInstance()
    }
}