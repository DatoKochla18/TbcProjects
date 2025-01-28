package com.example.tbcexercises.presentation.userListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcexercises.data.model.User
import com.example.tbcexercises.data.network.RetrofitInstance
import com.example.tbcexercises.data.source.UserPagingSource
import kotlinx.coroutines.flow.Flow

class UserListViewModel : ViewModel() {

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 6, prefetchDistance = 1),
        pagingSourceFactory = { UserPagingSource(RetrofitInstance.api) }
    ).flow.cachedIn(viewModelScope)
}