package com.iraklyoda.categoryapp.data.remote.api

import com.iraklyoda.categoryapp.data.remote.dto.CategoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryService {
    @GET("https://run.mocky.io/v3/499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @GET("https://run.mocky.io/v3/499e0ffd-db69-4955-8d86-86ee60755b9c")
    suspend fun searchCategories(@Query("query") query: String): Response<List<CategoryDto>>
}