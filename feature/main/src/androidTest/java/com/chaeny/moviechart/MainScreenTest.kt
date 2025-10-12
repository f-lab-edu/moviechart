package com.chaeny.moviechart

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
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

    @Test
    fun topBar_displaysMovieText() {
        composeTestRule.setContent {
            TopBar()
        }

        composeTestRule.onNodeWithText("MOVIE").assertIsDisplayed()
    }

    @Test
    fun topBar_searchIcon_hasContentDescription() {
        composeTestRule.setContent {
            TopBar()
        }

        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
    }

    @Test
    fun dailySelected_clickWeekly_callsOnTypeSelected() {
        var clicked = false
        composeTestRule.setContent {
            PeriodTypes(
                selectedType = PeriodType.DAILY,
                onTypeSelected = { clicked = true }
            )
        }
        composeTestRule.onNodeWithText("주간").performClick()
        assert(clicked)
    }
}
