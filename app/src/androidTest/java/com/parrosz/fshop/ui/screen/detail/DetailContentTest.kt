package com.parrosz.fshop.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.parrosz.fshop.model.FShop
import com.parrosz.fshop.model.OrderFShop
import com.parrosz.fshop.R
import com.parrosz.fshop.onNodeWithStringId
import com.parrosz.fshop.ui.theme.FShopTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeOrderFShop = OrderFShop(
        fshop = FShop(2, R.drawable.kaos_oversize, "Kaos Polos Oversize Hitam", "Kaos polos oversize bahan cotton combeds 24s. Memberikan kesan santai namun tetap stylish. Desain yang simpel membuatnya mudah dipadukan dengan berbagai gaya fashion.", 55000),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FShopTheme {
                DetailContent(
                    fakeOrderFShop.fshop.image,
                    fakeOrderFShop.fshop.title,
                    fakeOrderFShop.fshop.harga,
                    fakeOrderFShop.fshop.deskripsi,
                    fakeOrderFShop.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderFShop.fshop.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.harga,
                fakeOrderFShop.fshop.harga
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }}


