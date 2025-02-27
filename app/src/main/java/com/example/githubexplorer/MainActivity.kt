package com.example.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubexplorer.data.DETAILS
import com.example.githubexplorer.data.MAIN
import com.example.githubexplorer.screens.DetailsScreen
import com.example.githubexplorer.screens.MainScreen
import com.example.githubexplorer.ui.theme.GitHubExplorerTheme
import com.example.githubexplorer.viewmodels.DetailViewModel
import com.example.githubexplorer.viewmodels.MainViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GitHubExplorerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(mainViewModel: MainViewModel = hiltViewModel(), detailViewModel: DetailViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = MAIN
    ) {
        composable(
            route = MAIN,
            enterTransition = {
                fadeIn(animationSpec = tween(2500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(2500))
            }
        ) {
            MainScreen(mainViewModel, navController)
        }
        composable(
            route = DETAILS,
            enterTransition = {
                fadeIn(animationSpec = tween(2500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(2500))
            }
        ) {
            DetailsScreen(detailViewModel)
        }
    }
}
