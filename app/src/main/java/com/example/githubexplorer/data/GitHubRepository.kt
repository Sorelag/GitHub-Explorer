package com.example.githubexplorer.data

import com.example.githubexplorer.networking.GithubApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GithubApi) {

    fun getRepositories(cursor: String? = null): Flow<List<Repository>> = flow {
        val query = """
        query GetFewRepositories {
          search(query: "stars:>1000 sort:stars-desc", type: REPOSITORY, first: 5) {
            edges {
              node {
                ... on Repository {
                  name
                  description
                  stargazerCount
                  owner {
                    login
                    avatarUrl
                  }
                }
              }
            }
          }
        }
        """.trimIndent()
        val variables = mapOf("cursor" to (cursor ?: ""))
        val request = Request(query, variables)
        val response = api.executeQuery(request)
        val repositories = response.data?.search?.edges?.mapNotNull { edge ->
            edge.node?.let { node ->
                Repository(
                    name = node.name ?: "",
                    description = node.description,
                    stargazerCount = node.stargazerCount ?: 0,
                    forkCount = node.forkCount ?: 0,
                    primaryLanguage = node.primaryLanguage?.name,
                    owner = node.owner?.login ?: "",
                    avatarUrl = node.owner?.avatarUrl ?: ""
                )
            }
        } ?: emptyList()
        emit(repositories)
    }
}