package com.moucan.common.http

import com.google.gson.annotations.SerializedName

class GitRepoResponse {
    @SerializedName("items") val items: List<GitRepo> = emptyList()
}