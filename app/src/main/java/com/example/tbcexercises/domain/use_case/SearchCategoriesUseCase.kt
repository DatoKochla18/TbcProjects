package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.domain.repository.CategoryRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<GetCategories>>> {
        return categoryRepository.getCategories()
            .map { resource ->
                resource.mapper { categories ->
                    if (query.isNotEmpty()) {
                        searchInCategories(categories, query.lowercase())
                    } else {
                        categories
                    }
                }
            }
    }

    private fun searchInCategories(
        categories: List<GetCategories>,
        query: String
    ): List<GetCategories> {
        return categories.mapNotNull { category ->
            val matches = category.name.lowercase().contains(query)
            val filteredChildren = searchInCategories(category.children, query)
            if (matches || filteredChildren.isNotEmpty()) {
                category.copy(children = filteredChildren)
            } else {
                null
            }
        }
    }
}