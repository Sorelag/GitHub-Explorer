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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubexplorer.data.Repository
import com.example.githubexplorer.ui.theme.GitHubExplorerTheme
import com.example.githubexplorer.views.RepositoryListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubExplorerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(Modifier.padding(16.dp)) {
                        AppName(modifier = Modifier.padding(16.dp))
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
        text = stringResource(R.string.app_name),
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
            RepositoryListItem(repository = repository)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitHubExplorerTheme {
        TrendingRepositoriesScreen()
    }
}
