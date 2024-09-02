package com.nmi.lexiloop.presentation.home

import com.nmi.lexiloop.model.user.User
import com.nmi.lexiloop.util.NetworkError

data class HomeScreenState(
    val isSignedIn: Boolean = false,
    val permissionDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: NetworkError? = null,
    val user: User = User("john.doe@email.com", "johndoe", "John Doe")
)