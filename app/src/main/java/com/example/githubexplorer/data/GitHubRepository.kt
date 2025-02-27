package com.example.githubexplorer.data

import com.example.githubexplorer.networking.GithubApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Basic queries
class GitHubRepository @Inject constructor(private val api: GithubApi) {

    fun getRepositories(cursor: String? = null): Flow<List<Repository>> = flow {
        val query = """
        query GetFewRepositories {
          search(query: "stars:>1000 sort:stars-desc", type: REPOSITORY, first: 10) {
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
                  primaryLanguage {
                    name
                  }
                }
              }
            }
          }
        }
        """.trimIndent()
        val variables = mapOf("cursor" to (cursor ?: ""))
        val response = api.getRepositoryData(Request(query, variables))
        val repositories = response.data?.search?.edges?.mapNotNull { edge ->
            edge.node?.let { node ->
                Repository(
                    name = node.name ?: "",
                    description = node.description,
                    stargazerCount = node.stargazerCount ?: 0,
                    primaryLanguage = node.primaryLanguage?.name,
                    avatarUrl = node.owner?.avatarUrl ?: "",
                    owner = node.owner?.login ?: ""
                )
            }
        } ?: emptyList()
        emit(repositories)
    }

    fun getRepositoryDetails(owner: String, repoName: String): Flow<RepositoryInfo> = flow {
        val query = """
                    query GetRepositoryDetails(${'$'}owner: String!, ${'$'}repoName: String!) {
                      repository(owner: ${'$'}owner, name: ${'$'}repoName) {
                        id
                        name
                        description
                        url
                        stargazerCount
                        forkCount
                        primaryLanguage {
                          name
                        }
                        owner {
                          login
                          avatarUrl
                        }
                        issues(last: 5, orderBy: {field: CREATED_AT, direction: DESC}) {
                          nodes {
                            title
                            url
                            createdAt
                            author {
                              login
                              avatarUrl
                            }
                          }
                        }
                        collaborators(first: 5, affiliation: DIRECT) {
                          nodes {
                            login
                            avatarUrl
                          }
                        }
                      }
                    }
                """.trimIndent()

        val variables = mapOf("owner" to owner, "repoName" to repoName)
        val response = api.getRepositoryDetails(Request(query, variables))

        response.data?.repository?.let { repo ->
            val info = RepositoryInfo(
                id = repo.id,
                name = repo.name,
                description = repo.description,
                url = repo.url,
                stargazerCount = repo.stargazerCount,
                primaryLanguage = repo.primaryLanguage,
                owner = repo.owner,
                issues = repo.issues,
                collaborators = repo.collaborators,
            )
            emit(info)
        }
    }

    fun starRepository(repositoryId: String): Flow<StarData?> = flow {
        val mutation = """
                    mutation StarRepository(${'$'}repositoryId: ID!) {
                      addStar(input: {starrableId: ${'$'}repositoryId}) {
                        starrable {
                          stargazerCount
                        }
                      }
                    }
                """.trimIndent()

        val variables = mapOf<String, Any>("repositoryId" to repositoryId)
        val requestBody = Request(mutation, variables)
        val response = api.addStar(requestBody)
        emit(response.data?.data)
    }

}
