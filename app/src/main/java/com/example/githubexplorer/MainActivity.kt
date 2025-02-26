package com.example.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubexplorer.data.Repository
import com.example.githubexplorer.ui.theme.GitHubExplorerTheme
import com.example.githubexplorer.ui.theme.titleStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubExplorerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        AppName(modifier = Modifier.padding(innerPadding))
                        TrendingRepositoriesScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AppName(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name, titleStyle),
        modifier = modifier
    )
}


@Composable
fun TrendingRepositoriesScreen(viewModel: MainViewModel = hiltViewModel()) {
    val repositories = viewModel.repositories.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val error = viewModel.error.collectAsState()

    Column {
        if (isLoading.value) {
            Text("Loading...")
        } else if (error.value != null) {
            Text("Error: $error")
        } else {
            RepositoryList(repositories = repositories.value)
        }
    }
}

@Composable
fun RepositoryList(repositories: List<Repository>) {
    LazyColumn {
        items(repositories) { repository ->
            RepositoryItem(repository = repository)
        }
    }
}

@Composable
fun RepositoryItem(repository: Repository) {
    Column {
        Text(text = repository.name)
        Text(text = repository.description ?: "No description")
        Text(text = "Stars: ${repository.stargazerCount}")
        Text(text = "Forks: ${repository.forkCount}")
        Text(text = "Language: ${repository.primaryLanguage ?: "N/A"}")
        Text(text = "Owner: ${repository.owner}")
        Text(text = "URL: ${repository.avatarUrl}")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitHubExplorerTheme {
        TrendingRepositoriesScreen()
    }
}
