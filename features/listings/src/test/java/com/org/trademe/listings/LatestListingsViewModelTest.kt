package com.org.trademe.listings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.org.trademe.core.network.Result
import com.org.trademe.listings.data.model.ListingApiModel
import com.org.trademe.listings.data.model.ListResponse
import com.org.trademe.listings.domain.LatestListingsUseCase
import com.org.trademe.listings.domain.MockLatestListingsUseCase
import com.org.trademe.listings.presentation.LatestListingsViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LatestListingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val sampleApiModels = listOf(
        ListingApiModel(
            ListingId = 1L,
            Title = "iPhone 14 Pro Max",
            Region = "Auckland",
            PhotoUrls = listOf("https://example.com/iphone.jpg"),
            PriceDisplay = "$1200.00",
            BuyNowPrice = 1100.0,
            HasBuyNow = true,
            IsClassified = true
        ),
        ListingApiModel(
            ListingId = 2L,
            Title = "Mountain Bike",
            Region = "Wellington",
            PhotoUrls = listOf("https://example.com/bike.jpg"),
            PriceDisplay = "$750.00",
            BuyNowPrice = null,
            HasBuyNow = false,
            IsClassified = false
        )
    )

    private val sampleResponse = ListResponse(
        List = sampleApiModels,
        Page = 1,
        PageSize = 20,
        TotalCount = 2
    )

    private lateinit var mockLatestListingsUseCase: LatestListingsUseCase
    private lateinit var mockGetLatestListingsUseCase: MockLatestListingsUseCase
    private lateinit var viewModel: LatestListingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockLatestListingsUseCase = mockk<LatestListingsUseCase>()
        mockGetLatestListingsUseCase = mockk<MockLatestListingsUseCase>()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `viewModel initializes and calls use case`() = runTest {
        // Given
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(sampleResponse))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { mockLatestListingsUseCase.getLatestListings() }
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertNull(uiState.error)
    }

    @Test
    fun `success state updates UI correctly`() = runTest {
        // Given
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(sampleResponse))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertEquals("iPhone 14 Pro Max", uiState.listings[0].title)
        assertEquals("Mountain Bike", uiState.listings[1].title)
        assertEquals("$1200.00", uiState.listings[0].priceDisplay)
        assertEquals("Auckland", uiState.listings[0].region)
        assertEquals("1100.0", uiState.listings[0].buyNowPrice)
        assertNull(uiState.listings[1].buyNowPrice)
        assertTrue(uiState.listings[0].isClassified)
        assertFalse(uiState.listings[1].isClassified)
        assertNull(uiState.error)
    }

    @Test
    fun `loading state is handled correctly`() = runTest {
        // Given
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Loading)

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState.isLoading)
        assertTrue(uiState.listings.isEmpty())
        assertNull(uiState.error)
    }

    @Test
    fun `error state handles network failure`() = runTest {
        // Given
        val errorMessage = "Network connection failed"
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Error(errorMessage))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
        assertTrue(uiState.listings.isEmpty())
    }

    @Test
    fun `empty state shows when no listings available`() = runTest {
        // Given
        val emptyResponse = ListResponse(
            List = emptyList(),
            Page = 1,
            PageSize = 20,
            TotalCount = 0
        )
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(emptyResponse))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertTrue(uiState.listings.isEmpty())
        assertNull(uiState.error)
    }

    @Test
    fun `retry calls use case again`() = runTest {
        // Given
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(sampleResponse))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When retry is called
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { mockLatestListingsUseCase.getLatestListings() }
    }

    @Test
    fun `buy now price handling works correctly`() = runTest {
        // Given
        val apiModelsWithDifferentBuyNow = listOf(
            ListingApiModel(
                ListingId = 1L,
                Title = "Item with BuyNow",
                Region = "Auckland",
                PhotoUrls = emptyList(),
                PriceDisplay = "$100",
                BuyNowPrice = 150.0,
                HasBuyNow = true,
                IsClassified = false
            ),
            ListingApiModel(
                ListingId = 2L,
                Title = "Item without BuyNow flag",
                Region = "Wellington",
                PhotoUrls = emptyList(),
                PriceDisplay = "$200",
                BuyNowPrice = 250.0,
                HasBuyNow = false,
                IsClassified = false
            )
        )
        val response = ListResponse(
            List = apiModelsWithDifferentBuyNow,
            Page = 1,
            PageSize = 20,
            TotalCount = 2
        )
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(response))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(2, uiState.listings.size)

        // Item 1: Has BuyNow = true and price = not null
        assertEquals("150.0", uiState.listings[0].buyNowPrice)

        // Item 2: Has BuyNow = false (should be null even though price exists)
        assertNull(uiState.listings[1].buyNowPrice)
    }

    @Test
    fun `handles null values in API model gracefully`() = runTest {
        // Given
        val apiModelsWithNulls = listOf(
            ListingApiModel(
                ListingId = 1L,
                Title = "Valid Title",
                Region = null,
                PhotoUrls = null,
                PriceDisplay = null,
                BuyNowPrice = null,
                HasBuyNow = null,
                IsClassified = null
            )
        )
        val responseWithNulls = ListResponse(
            List = apiModelsWithNulls,
            Page = 1,
            PageSize = 20,
            TotalCount = 1
        )
        coEvery { mockLatestListingsUseCase.getLatestListings() } returns
                flowOf(Result.Success(responseWithNulls))

        // When
        viewModel = LatestListingsViewModel(mockGetLatestListingsUseCase, mockLatestListingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(1, uiState.listings.size)

        val listing = uiState.listings[0]
        assertEquals("1", listing.id)
        assertEquals("Valid Title", listing.title)
        assertEquals("Unknown", listing.region)
        assertTrue(listing.photoUrls.isEmpty())
        assertEquals("N/A", listing.priceDisplay)
        assertNull(listing.buyNowPrice)
        assertFalse(listing.isClassified)
        assertNull(uiState.error)
    }
}