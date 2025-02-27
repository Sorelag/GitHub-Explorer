package com.example.githubexplorer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.githubexplorer.viewmodels.MainViewModel
import com.example.githubexplorer.R
import com.example.githubexplorer.data.Repository
import com.example.githubexplorer.views.RepositoryListItem

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    with(viewModel) {
        val repositories = repositories.collectAsState().value
        val isLoading = isLoading.collectAsState().value
        val error = error.collectAsState().value

        Column(Modifier.padding(16.dp)) {
            AppName()
            if (error != null) Text("Error: $error")
            RepositoryList(repositories = repositories, viewModel, isLoading, navController, onRefresh = { getRepositories() })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryList(
    repositories: List<Repository>,
    viewModel: MainViewModel,
    isLoading: Boolean,
    navController: NavHostController,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isLoading,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        LazyColumn {
            items(repositories) { repository ->
                RepositoryListItem(repository = repository, viewModel, navController)
            }
        }
    }
}
