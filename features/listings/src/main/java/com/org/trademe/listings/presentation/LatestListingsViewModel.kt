package com.org.trademe.listings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.trademe.core.network.Result
import com.org.trademe.listings.data.model.LatestListingsUiState
import com.org.trademe.listings.data.model.Listing
import com.org.trademe.listings.data.model.ListingApiModel
import com.org.trademe.listings.domain.LatestListingsUseCase
import com.org.trademe.listings.domain.MockLatestListingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LatestListingsViewModel @Inject constructor(
    private val getLatestListingsUseCase: MockLatestListingsUseCase,
    private val latestListingsUseCase: LatestListingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestListingsUiState())
    val uiState: StateFlow<LatestListingsUiState> = _uiState.asStateFlow()

    init {
        // loadMockListings()
        fetchLatestListings()
    }


    private fun fetchLatestListings() {
        viewModelScope.launch {
            latestListingsUseCase.getLatestListings().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    is Result.Success -> {
                        val listings = result.data.List
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            listings = listings.mapNotNull { apiModel ->
                                try {
                                    mapToDomainModel(apiModel)
                                } catch (e: Exception) {
                                    // Log the error but don't crash
                                    null
                                }
                            },
                            error = null
                        )
                    }

                    is Result.Empty -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            listings = emptyList(),
                            error = "${result.title}: ${result.message}"
                        )
                    }

                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    private fun mapToDomainModel(apiModel: ListingApiModel): Listing {
        return Listing(
            id = apiModel.ListingId?.toString() ?: "",
            title = apiModel.Title ?: "",
            region = apiModel.Region ?: "Unknown",
            photoUrls = apiModel.PhotoUrls ?: emptyList(),
            priceDisplay = apiModel.PriceDisplay ?: "N/A",
            buyNowPrice = if (apiModel.HasBuyNow == true && apiModel.BuyNowPrice != null)
                apiModel.BuyNowPrice.toString()
            else null,
            isClassified = apiModel.IsClassified ?: false
        )
    }

    private fun loadMockListings() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getLatestListingsUseCase.getList()
                .onSuccess { listings ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        listings = listings,
                        error = null
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
        }
    }

    fun retry() {
        //loadMockListings()
        fetchLatestListings()
    }
}