package com.example.githubexplorer.data

// Request data
data class Request(val query: String, val variables: Map<String, Any>? = null)

data class GraphQLResponse<T>(val data: T?)

data class Repository(
    val name: String,
    val description: String?,
    val stargazerCount: Int,
    val primaryLanguage: String?,
    val avatarUrl: String?,
    val owner: String
)

data class Data(val search: Search?)

data class Search(val pageInfo: PageInfo?, val edges: List<Edge>?)

data class PageInfo(val hasNextPage: Boolean?, val endCursor: String?)

data class Edge(val node: Node?)

data class Node(
    val name: String?,
    val description: String?,
    val stargazerCount: Int?,
    val primaryLanguage: PrimaryLanguage?,
    val owner: Owner?
)

data class PrimaryLanguage(val name: String?)

data class Owner(val login: String?, val avatarUrl: String?)

data class RepositoryData(
    val repository: RepositoryInfo?
)

data class RepositoryInfo(
    val id : String,
    val name: String,
    val description: String?,
    val url: String,
    val stargazerCount: Int,
    val primaryLanguage: PrimaryLanguage?,
    val owner: Owner,
    val issues: Issues,
    val collaborators: Collaborators?
)

data class Issues(
    val nodes: List<Issue>
)

data class Issue(
    val title: String,
    val url: String,
    val createdAt: String,
    val author: Author?
)

data class Author(
    val login: String,
    val avatarUrl: String?
)

data class Collaborators(
    val nodes: List<Contributor?>?
)

data class Contributor(
    val login: String,
    val avatarUrl: String,
    val contributionsCollection: ContributionsCollection
)

data class ContributionsCollection(
    val totalCommitContributions: Int
)

data class StarRepositoryResult(
    val data: StarData?
)

data class StarData(
    val addStar: AddStar?
)

data class AddStar(
    val starrable: Starrable?
)

data class Starrable(
    val stargazerCount: Int
)
