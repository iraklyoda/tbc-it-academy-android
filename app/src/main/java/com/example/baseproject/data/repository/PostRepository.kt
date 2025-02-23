package com.example.baseproject.data.repository

import com.example.baseproject.common.ApiHelper
import com.example.baseproject.common.Resource
import com.example.baseproject.data.mapper.toPostUi
import com.example.baseproject.data.remote.api.PostService
import com.example.baseproject.ui.home.post.Post
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class PostRepository @Inject constructor(
    private val postService: PostService,
    private val apiHelper: ApiHelper
) {
    suspend fun getPosts(): Flow<Resource<List<Post>>> {
        return apiHelper.handleHttpRequest { postService.getPosts() }
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Resource.Success(resource.data.map { it.toPostUi() })
                    is Resource.Error -> Resource.Error(resource.errorMessage)
                    is Resource.Loading -> Resource.Loading(resource.loading)
                }
            }
    }
}