package com.moucan.common.http

import com.moucan.common.response.GitRepoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitService {
    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): GitRepoResponse

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GitService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitService::class.java)
        }
    }
}