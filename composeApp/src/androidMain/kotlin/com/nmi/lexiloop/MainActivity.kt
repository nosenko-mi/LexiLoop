package com.nmi.lexiloop

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nmi.lexiloop.presentation.feedback.FeedbackScreen
import com.nmi.lexiloop.presentation.home.HomeScreen
import com.nmi.lexiloop.presentation.quiz_session.QuizSessionScreen
import com.nmi.lexiloop.presentation.ui.theme.LexiLoopAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        enableEdgeToEdge()
        setContent {
            LexiLoopAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.QuizSessionNavigationScreen
                ) {
                    composable<Route.HomeNavigationScreen> {
                        HomeScreen()
                    }

                    composable<Route.FeedbackNavigationScreen> {
                        FeedbackScreen()
                    }

                    composable<Route.QuizSessionNavigationScreen> {
                        QuizSessionScreen(onCloseClick = {
                            // exit session, pop backstack
                        })
                    }
                }
            }
        }
    }
}

