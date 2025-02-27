package com.example.githubexplorer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.githubexplorer.R
import com.example.githubexplorer.data.Issue
import com.example.githubexplorer.data.RepositoryInfo

@Composable
fun RepositoryDetailItem(
    repositoryDetails: RepositoryInfo,
    onButtonClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = repositoryDetails.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            if (!repositoryDetails.description.isNullOrBlank()) {
                Text(
                    text = repositoryDetails.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = "No description available",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Button(
                onClick = onButtonClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.add_star))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Issues List
            Text(
                text = "Issues:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            with(repositoryDetails.issues.nodes) {
                if (isEmpty())
                    Text(
                        text = "N/A",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                else
                    LazyColumn {
                        items(repositoryDetails.issues.nodes) { issue ->
                            IssueItem(issue)
                        }
                    }
            }
        }
    }
}

@Composable
fun IssueItem(issue: Issue) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = issue.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "URL: ${issue.url}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Created At: ${issue.createdAt}",
                style = MaterialTheme.typography.bodySmall
            )
            if (issue.author != null) {
                Text(
                    text = "Author: ${issue.author.login}",
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Text(
                    text = "Author: Unknown",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}