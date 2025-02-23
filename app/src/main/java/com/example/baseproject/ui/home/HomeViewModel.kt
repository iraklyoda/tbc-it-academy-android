package com.example.baseproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.common.Resource
import com.example.baseproject.data.repository.LocationRepository
import com.example.baseproject.data.repository.PostRepository
import com.example.baseproject.ui.home.location.Location
import com.example.baseproject.ui.home.post.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _locationsStateFlow: MutableStateFlow<Resource<List<Location>>> =
        MutableStateFlow(Resource.Loading(false))
    val locationsStateFlow get() = _locationsStateFlow

    private val _postsStateFlow: MutableStateFlow<Resource<List<Post>>> =
        MutableStateFlow(Resource.Loading(false))
    val postsStateFlow get() = _postsStateFlow

    init {
        viewModelScope.launch {
            locationRepository.getLocations().collectLatest { resource ->
                _locationsStateFlow.emit(resource)
            }

            postRepository.getPosts().collectLatest { resource ->
                _postsStateFlow.emit(resource)
            }
        }
    }
}