package com.iraklyoda.categoryapp.domain.use_case.category

import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.model.GetCategory
import com.iraklyoda.categoryapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SearchCategoriesUseCase {
    suspend operator fun invoke(query: String): Flow<Resource<List<GetCategory>>>
}

class SearchCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : SearchCategoriesUseCase {
    override suspend fun invoke(query: String): Flow<Resource<List<GetCategory>>> {
        return categoryRepository.searchCategories(query = query).map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Resource.Success(resource.data.filter { category ->
                        category.name.contains(query, ignoreCase = true)
                    })
                }

                is Resource.Loading -> {
                    Resource.Loading(loading = resource.loading)
                }

                is Resource.Error -> {
                    Resource.Error(errorMessage = resource.errorMessage)
                }
            }
        }
    }
}