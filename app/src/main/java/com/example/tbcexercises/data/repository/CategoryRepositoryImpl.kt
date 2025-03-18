package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomainWithDepth
import com.example.tbcexercises.data.remote.service.CategoryService
import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.domain.repository.CategoryRepository
import com.example.tbcexercises.utils.Resource
import com.example.tbcexercises.utils.handleNetworkRequest
import com.example.tbcexercises.utils.mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryService: CategoryService) :
    CategoryRepository {
    override fun getCategories(): Flow<Resource<List<GetCategories>>> {
        return handleNetworkRequest { categoryService.getCategories() }.map { resource ->
            resource.mapper { categoryResponses ->
                categoryResponses.map { it.toDomainWithDepth() }
            }
        }
    }
}