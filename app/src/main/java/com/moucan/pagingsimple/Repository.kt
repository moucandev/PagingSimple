package com.moucan.pagingsimple

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

object Repository {
    private const val PAGE_SIZE = 50

    private val gitService = GitService.create()

    fun getPageDate(): Flow<PagingData<GitRepo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { RepoPagingSource(gitService) }
        ).flow
    }
}