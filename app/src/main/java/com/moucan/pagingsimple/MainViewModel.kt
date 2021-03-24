package com.moucan.pagingsimple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    fun getData(): Flow<PagingData<GitRepo>> {
        return Repository.getPageDate().cachedIn(viewModelScope)
    }
}