package com.moucan.pagingsimple

import com.google.gson.annotations.SerializedName

class GitRepoResponse {
    @SerializedName("items") val items: List<GitRepo> = emptyList()
}