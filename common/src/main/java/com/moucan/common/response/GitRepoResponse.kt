package com.moucan.common.response

import com.google.gson.annotations.SerializedName
import com.moucan.common.repo.GitRepo
import java.io.Serializable

class GitRepoResponse : Serializable{
    @SerializedName("items") val items: List<GitRepo> = emptyList()
}