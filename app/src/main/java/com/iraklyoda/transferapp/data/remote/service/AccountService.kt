package com.iraklyoda.transferapp.data.remote.service

import com.iraklyoda.transferapp.data.remote.dto.AccountDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountService {
    @GET("d689fe3e-6faf-446a-9896-c538de3449fa")
    suspend fun getAccounts(): Response<List<AccountDto>>

    @GET("d9edd158-b60b-4e1c-9576-8ccc26d2b427")
    suspend fun getReceiverAccount(@Query("identifier") identifier: String): Response<AccountDto>
}