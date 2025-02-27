package com.example.githubexplorer.networking

import com.example.githubexplorer.data.Data
import com.example.githubexplorer.data.GraphQLResponse
import com.example.githubexplorer.data.RepositoryData
import com.example.githubexplorer.data.Request
import com.example.githubexplorer.data.StarRepositoryResult
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubApi {

    companion object {
        const val TOKEN =
            "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" //Replace with your token
    }

    @POST("graphql")
    @Headers("Content-Type: application/json", "Authorization: Bearer $TOKEN")
    suspend fun executeQuery(@Body request: Request): GraphQLResponse<Data>

    @POST("graphql")
    @Headers("Content-Type: application/json", "Authorization: Bearer $TOKEN")
    suspend fun executeQuery2(@Body body: Request): GraphQLResponse<RepositoryData>

    @POST("graphql")
    @Headers("Content-Type: application/json", "Authorization: Bearer $TOKEN")
    suspend fun executeMutation(@Body body: Request): GraphQLResponse<StarRepositoryResult>
}
