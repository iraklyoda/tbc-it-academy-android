package com.iraklyoda.categoryapp.domain.repository

import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.model.GetCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Resource<List<GetCategory>>>
    suspend fun searchCategories(query: String): Flow<Resource<List<GetCategory>>>
}