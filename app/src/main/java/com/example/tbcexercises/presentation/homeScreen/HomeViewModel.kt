package com.example.tbcexercises.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcexercises.data.remote.response.UserResponse
import com.example.tbcexercises.data.remote.RetrofitInstance
import com.example.tbcexercises.data.source.UserPagingSource
import com.example.tbcexercises.domain.model.User
import kotlinx.coroutines.flow.Flow


class HomeViewModel : ViewModel() {

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 6, prefetchDistance = 1),
        pagingSourceFactory = { UserPagingSource(RetrofitInstance.userApi) }
    ).flow.cachedIn(viewModelScope)
}