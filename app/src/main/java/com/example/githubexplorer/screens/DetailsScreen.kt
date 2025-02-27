package com.example.githubexplorer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.githubexplorer.viewmodels.DetailViewModel
import com.example.githubexplorer.views.RepositoryDetailItem

@Composable
fun DetailsScreen(viewModel: DetailViewModel) {
    with(viewModel) {
        val info = info.collectAsState().value
        val isLoading = isLoading.collectAsState().value
        val error = error.collectAsState().value

        val refreshCount by remember { mutableIntStateOf(0) }
        LaunchedEffect(key1 = refreshCount) {
            viewModel.makeRequest()
        }

        Column(Modifier.padding(16.dp)) {
            if (isLoading) Text("Loading...")
            if (error != null) Text("Error: $error")
            info?.let { RepositoryDetailItem(it, onButtonClicked = {viewModel.starRequest()}) }
        }
    }
}