package com.org.trademe.listings.data.model

data class LatestListingsUiState(
    val isLoading: Boolean = false,
    val listings: List<Listing> = emptyList(),
    val error: String? = null
)