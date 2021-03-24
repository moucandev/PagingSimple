package com.moucan.pagingsimple

import androidx.paging.PagingSource
import androidx.paging.PagingState

class RepoPagingSource(private val gitService: GitService) : PagingSource<Int, GitRepo>() {
    override fun getRefreshKey(state: PagingState<Int, GitRepo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitRepo> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = gitService.searchRepos(page, pageSize)
            val repoItem = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItem.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItem, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}