package com.org.trademe.listings.data.service

import com.org.trademe.listings.data.model.ListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ListingsApiService {
    @GET("listings/latest.json?photo_size=List")
    fun fetchList(): Deferred<Response<ListResponse>>
}