package com.chaeny.moviechart

import androidx.annotation.StringRes

internal enum class TabType(@StringRes val tabResId: Int) {
    DAILY(R.string.daily),
    WEEKLY(R.string.weekly)
}
