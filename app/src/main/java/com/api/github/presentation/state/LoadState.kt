package com.api.github.presentation.state

internal sealed class LoadState {
    object ShowLoading: LoadState()
    object HideLoading: LoadState()
}