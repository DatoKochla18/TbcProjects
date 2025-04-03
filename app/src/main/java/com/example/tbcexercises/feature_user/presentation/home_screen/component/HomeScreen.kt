package com.example.tbcexercises.feature_user.presentation.home_screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.tbcexercises.feature_user.presentation.home_screen.HomeViewModel
import com.example.tbcexercises.feature_user.presentation.model.User


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val usersPagingItems = viewModel.users.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(items = usersPagingItems) {
            it?.let { UserItem(it) }
        }


        usersPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = error.error.localizedMessage ?: "Unknown Error",
                            onClickRetry = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = error.error.localizedMessage ?: "Unknown Error",
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun HomeScreenPreview() {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        val dummyUsers = listOf(
            User(
                id = 1,
                email = "test1@example.com",
                fullName = "Test User 1",
                avatar = "https://example.com/avatar1.png"
            ),
            User(
                id = 2,
                email = "test2@example.com",
                fullName = "Test User 2",
                avatar = "https://example.com/avatar2.png"
            )
        )
        items(dummyUsers) { user ->
            user?.let { UserItem(user = it) }

        }
    }
}
