package com.example.tbcexercises.feature_user.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.tbcexercises.core.presentation.resource.Colors
import com.example.tbcexercises.core.presentation.resource.Dimens
import com.example.tbcexercises.feature_user.presentation.home_screen.component.ErrorItem
import com.example.tbcexercises.feature_user.presentation.home_screen.component.UserItem
import com.example.tbcexercises.feature_user.presentation.model.User

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        viewModel.users.collectAsLazyPagingItems(),
    )
}

@Composable
fun HomeScreen(
    usersPagingItems: LazyPagingItems<User>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.WHITE)
            .padding(
                horizontal = Dimens.SCREEN_HORIZONTAL, vertical = Dimens.SCREEN_TOP
            )

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.WHITE),
        ) {
            items(items = usersPagingItems) { user ->
                user?.let { UserItem(it) }
            }

            usersPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = loadState.refresh as LoadState.Error
                        item {
                            error.error.localizedMessage?.let {
                                ErrorItem(
                                    message = it,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = loadState.append as LoadState.Error
                        item {
                            error.error.localizedMessage?.let {
                                ErrorItem(
                                    message = it,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
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
                email = "sdjlsaks",
                fullName = "sdfkldsjf",
                avatar = "sadkljslkf"
            ),
            User(
                id = 2,
                email = "asdjsklfjsdlkf",
                fullName = "dkflsdjflkdsjf",
                avatar = "dklfjdsfldsjflk"
            )
        )
        items(dummyUsers) { user ->
            UserItem(user = user)

        }
    }
}
