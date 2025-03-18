package com.example.tbcexercises.data.repository

import com.example.tbcexercises.data.mapper.toDomain
import com.example.tbcexercises.data.remote.service.CategoryService
import com.example.tbcexercises.data.remote.util.ApiHelper
import com.example.tbcexercises.data.remote.util.mapper
import com.example.tbcexercises.domain.model.GetCategories
import com.example.tbcexercises.domain.repository.CategoryRepository
import com.example.tbcexercises.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val apiHelper: ApiHelper,
) :
    CategoryRepository {
    override fun getCategories(): Flow<Resource<List<GetCategories>>> {
        return apiHelper.handleNetworkRequest { categoryService.getCategories() }.map { resource ->
            resource.mapper { categoryResponses ->
                categoryResponses.map { it.toDomain() }
            }
        }
    }
}