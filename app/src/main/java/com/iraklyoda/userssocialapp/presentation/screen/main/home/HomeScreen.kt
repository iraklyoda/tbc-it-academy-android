package com.iraklyoda.userssocialapp.presentation.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.iraklyoda.userssocialapp.R
import com.iraklyoda.userssocialapp.presentation.screen.main.home.component.UserCard
import com.iraklyoda.userssocialapp.presentation.screen.main.home.model.UserUi
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.theme.UsersSocialAppTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val usersPagingItems = viewModel.usersPagingFlow.collectAsLazyPagingItems()

    HomeScreenContent(
        usersPagingItems = usersPagingItems,
        state = viewModel.state
    )
}

@Composable
fun HomeScreenContent(
    usersPagingItems: LazyPagingItems<UserUi>,
    state: HomeState,
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
        Text(
            text = "Home",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.SpacingMedium),
            textAlign = TextAlign.Center
        )


        LazyColumn {

            items(
                count = usersPagingItems.itemCount,
                key = usersPagingItems.itemKey { user -> user.id })
            { index ->
                val user = usersPagingItems[index]
                user?.let {
                    UserCard(user = it)
                }
            }

            // Loading State
            if (usersPagingItems.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            // No Users
            if (usersPagingItems.itemCount == 0 && !state.loader) {
                item {
                    Text(
                        text = stringResource(R.string.no_users_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    UsersSocialAppTheme {
        HomeScreenContent(
            state = HomeState(),
            usersPagingItems = emptyLazyPagingItems()
        )
    }
}

@Composable
fun emptyLazyPagingItems(): LazyPagingItems<UserUi> {
    val pagingData = remember { MutableStateFlow(PagingData.empty<UserUi>()) }
    return pagingData.collectAsLazyPagingItems()
}
