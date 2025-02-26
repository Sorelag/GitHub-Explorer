package com.example.githubexplorer.data

data class Request(val query: String, val variables: Map<String, Any>? = null)

data class Response(val data: Data?)

data class Repository(
    val name: String,
    val description: String?,
    val stargazerCount: Int,
    val primaryLanguage: String?,
    val avatarUrl: String?
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
