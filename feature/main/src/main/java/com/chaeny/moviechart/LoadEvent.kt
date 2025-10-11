package com.chaeny.moviechart

internal sealed class LoadEvent {
    data object NoInternet : LoadEvent()
    data object NetworkError : LoadEvent()
    data object NoResult : LoadEvent()
}
