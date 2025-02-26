package com.example.githubexplorer.networking

import com.example.githubexplorer.data.Request
import com.example.githubexplorer.data.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GithubApi {
    @Headers("Authorization: Bearer TOKEN") // Replace with your token
    @POST("graphql")
    suspend fun executeQuery(@Body request: Request): Response
}
