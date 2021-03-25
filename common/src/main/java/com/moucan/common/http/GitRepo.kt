package com.moucan.common.http

import com.google.gson.annotations.SerializedName

data class GitRepo (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val starCount: Int
    )