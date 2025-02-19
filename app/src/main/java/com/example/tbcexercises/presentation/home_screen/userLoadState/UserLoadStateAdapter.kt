package com.example.tbcexercises.presentation.home_screen.userLoadState

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class UserLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UserLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = UserLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: UserLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}