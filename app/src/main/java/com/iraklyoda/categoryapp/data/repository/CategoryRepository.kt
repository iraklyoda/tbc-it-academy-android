package com.iraklyoda.categoryapp.data.repository

import com.iraklyoda.categoryapp.data.mapper.toDomain
import com.iraklyoda.categoryapp.data.remote.api.CategoryService
import com.iraklyoda.categoryapp.data.remote.common.ApiHelper
import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.common.mapResource
import com.iraklyoda.categoryapp.domain.model.GetCategory
import com.iraklyoda.categoryapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val categoryService: CategoryService
) : CategoryRepository {
    override suspend fun getCategories(): Flow<Resource<List<GetCategory>>> {
        return apiHelper.handleHttpRequest { categoryService.getCategories() }
            .mapResource { categories ->
                categories.toDomain()
            }
    }

    override suspend fun searchCategories(query: String): Flow<Resource<List<GetCategory>>> {
        return apiHelper.handleHttpRequest { categoryService.searchCategories(query = query) }
            .mapResource { categories ->
                categories.toDomain()
            }
    }
}