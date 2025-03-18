package com.iraklyoda.categoryapp.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loading(val loading: Boolean) : Resource<Nothing>()
}

fun <DTO, DOMAIN> Flow<Resource<DTO>>.mapResource(mapper: (DTO) -> DOMAIN): Flow<Resource<DOMAIN>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Loading -> Resource.Loading(resource.loading)
            is Resource.Error -> Resource.Error(resource.errorMessage)
            is Resource.Success -> {
                Resource.Success(mapper(resource.data))
            }
        }
    }
}