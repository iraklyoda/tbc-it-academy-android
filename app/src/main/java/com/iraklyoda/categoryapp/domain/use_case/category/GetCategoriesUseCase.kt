package com.iraklyoda.categoryapp.domain.use_case.category

import com.iraklyoda.categoryapp.domain.common.Resource
import com.iraklyoda.categoryapp.domain.model.GetCategory
import com.iraklyoda.categoryapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCategoriesUseCase {
    suspend operator fun invoke(): Flow<Resource<List<GetCategory>>>
}

class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : GetCategoriesUseCase {
    override suspend fun invoke(): Flow<Resource<List<GetCategory>>> {
        return categoryRepository.getCategories()
    }
}