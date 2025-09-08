package com.org.trademe.listings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.trademe.listings.data.model.LatestListingsUiState
import com.org.trademe.listings.domain.LatestListingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestListingsViewModel @Inject constructor(
    private val getLatestListingsUseCase: LatestListingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestListingsUiState())
    val uiState: StateFlow<LatestListingsUiState> = _uiState.asStateFlow()

    init {
        loadListings()
    }

    private fun loadListings() {
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
        loadListings()
    }
}