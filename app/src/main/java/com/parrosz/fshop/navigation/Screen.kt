package com.parrosz.fshop.navigation

sealed class Screen (val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailFShop : Screen("home/{fshopId}") {
        fun createRoute(fshopId: Long) = "home/$fshopId"
    }
}
