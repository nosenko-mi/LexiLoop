package com.nmi.lexiloop.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nmi.lexiloop.QuizSDK


// about context injection
// https://stackoverflow.com/questions/53439111/how-to-inject-application-context-from-app-module-to-network-module-using-ko


class HomeScreenViewModel(
    private val sdk: QuizSDK,
) : ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    fun updatePermissionDialogVisibility(visibility: Boolean) {
        _state.value = _state.value.copy(permissionDialogVisible = visibility)
    }
}