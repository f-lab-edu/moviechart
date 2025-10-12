package com.chaeny.moviechart

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun periodTypes_displaysDailyAndWeekly() {
        composeTestRule.setContent {
            PeriodTypes(
                selectedType = PeriodType.DAILY,
                onTypeSelected = {}
            )
        }

        composeTestRule.onNodeWithText("일간").assertIsDisplayed()
        composeTestRule.onNodeWithText("주간").assertIsDisplayed()
    }
}
