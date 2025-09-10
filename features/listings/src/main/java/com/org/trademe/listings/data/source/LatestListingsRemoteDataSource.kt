package com.org.trademe.listings.data.source

import com.org.trademe.listings.data.model.ListResponse


interface LatestListingsRemoteDataSource {

    suspend fun getLatestListings(): ListResponse

}