package com.example.githubexplorer.networking

import com.example.githubexplorer.data.Request
import com.example.githubexplorer.data.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubApi {
    @Headers("Authorization: Bearer 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") // Replace with your token
    @POST("graphql")
    suspend fun executeQuery(@Body request: Request): Response
}
