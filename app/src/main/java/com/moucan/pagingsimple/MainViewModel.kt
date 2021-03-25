package com.moucan.pagingsimple

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moucan.common.base.BaseViewModel
import com.moucan.common.http.GitRepo
import com.moucan.common.http.Repository
import kotlinx.coroutines.flow.Flow

class MainViewModel: BaseViewModel() {
    fun getData(): Flow<PagingData<GitRepo>> {
        return Repository.getPageDate().cachedIn(viewModelScope)
    }
}