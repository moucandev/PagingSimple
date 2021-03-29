package com.moucan.common.repo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitRepo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("stargazers_count") val starCount: Int
    ): Serializable