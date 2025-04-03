package com.iraklyoda.userssocialapp.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.iraklyoda.userssocialapp.presentation.component.UserCard
import com.iraklyoda.userssocialapp.presentation.screen.home.model.UserUi
import com.iraklyoda.userssocialapp.presentation.theme.Dimens
import com.iraklyoda.userssocialapp.presentation.utils.CollectSideEffect


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit
) {
    val usersPagingItems = viewModel.usersFlow.collectAsLazyPagingItems()

    CollectSideEffect(flow = viewModel.sideEffect) { effect ->
        when (effect) {
            is HomeSideEffect.NavigateToProfile -> navigateToProfile()
        }

    }

    HomeScreenContent(
        usersPagingItems = usersPagingItems,
        state = viewModel.state,
        onEvent = { homeEvent ->
            viewModel.onEvent(event = homeEvent)
        }
    )
}

@Composable
fun HomeScreenContent(
    usersPagingItems: LazyPagingItems<UserUi>,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
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

                    items(count = usersPagingItems.itemCount,
                        key = usersPagingItems.itemKey { user -> user.id }) { index ->
                        val user = usersPagingItems[index]
                        user?.let {
                            UserCard(user = it)
                        }
                    }


                    if (usersPagingItems.loadState.append is LoadState.Loading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }

                    if (usersPagingItems.itemCount == 0 && !state.loader) {
                        item {
                            Text(
                                text = "No users found",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            IconButton(
                onClick = {
                    onEvent(HomeEvent.ProfileBtnClicked)
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Dimens.SpacingMedium)
                    .size(52.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, 
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


