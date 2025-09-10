package com.org.trademe.listings.data.model

data class ListResponse(
    val List: List<ListingApiModel>,
    val Page: Int,
    val PageSize: Int,
    val TotalCount: Int
)


data class ListingApiModel(
    val ListingId: Long,
    val Title: String,
    val Region: String?,
    val PhotoUrls: List<String>?,
    val PriceDisplay: String?,
    val BuyNowPrice: Double?,
    val HasBuyNow: Boolean?,
    val IsClassified: Boolean?
)


data class Listing(
    val id: String,
    val title: String,
    val region: String,
    val photoUrls: List<String>,
    val priceDisplay: String,
    val buyNowPrice: String? = null,
    val isClassified: Boolean = false
)