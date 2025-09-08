package com.org.trademe.listings.data.model

data class Listing(
    val id: String,
    val title: String,
    val region: String,
    val photoUrls: List<String>,
    val priceDisplay: String,
    val buyNowPrice: String? = null,
    val isClassified: Boolean = false
)