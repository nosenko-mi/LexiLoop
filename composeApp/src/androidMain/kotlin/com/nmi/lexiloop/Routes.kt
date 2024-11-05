package com.nmi.lexiloop

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeNavigationScreen: Route

    @Serializable
    data object FeedbackNavigationScreen: Route

    @Serializable
    data object QuizSessionNavigationScreen: Route
}