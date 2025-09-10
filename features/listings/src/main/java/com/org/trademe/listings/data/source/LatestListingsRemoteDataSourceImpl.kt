package com.org.trademe.listings.data.source

import com.org.trademe.core.exception.NoDataException
import com.org.trademe.core.network.qualifiers.IO
import com.org.trademe.listings.data.service.ListingsApiService
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LatestListingsRemoteDataSourceImpl @Inject constructor(
    private val service: ListingsApiService,
    @IO private val context: CoroutineContext
) : LatestListingsRemoteDataSource {
    override suspend fun getLatestListings() = withContext(context) {
        try {
            val response = service.fetchList().await()
            if (response.isSuccessful) {
                response.body() ?: throw NoDataException("Response body is null")
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw IOException("Network error occurred: ${e.message}", e)
        }
    }
}