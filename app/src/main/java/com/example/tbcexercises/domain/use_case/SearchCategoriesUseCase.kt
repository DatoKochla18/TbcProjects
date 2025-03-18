package com.example.tbcexercises.domain.use_case

import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.domain.repository.CategoryRepository
import com.example.tbcexercises.domain.util.Resource
import com.example.tbcexercises.data.remote.util.mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(query: String): Flow<Resource<List<GetCategories>>> {
        return categoryRepository.getCategories()
            .map { resource ->
                resource.mapper { categories ->
                    if (query.isNotEmpty()) {
                        searchInCategories(categories, query.lowercase()).sortedBy { it.depth }
                    } else {
                        listOf()
                    }
                }
            }
    }

    private fun searchInCategories(
        categories: List<GetCategories>,
        query: String,
    ): List<GetCategories> {
        return categories.flattenCategories().filter { it.name.lowercase().startsWith(query) }
    }

    private fun List<GetCategories>.flattenCategories(): List<GetCategories> {
        val result = mutableListOf<GetCategories>()

        fun addCategory(category: GetCategories) {
            result.add(category)
            category.children.forEach { child ->
                addCategory(child)
            }
        }
        for (category in this) {
            addCategory(category)
        }

        return result
    }
}