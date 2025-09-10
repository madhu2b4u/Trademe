package com.org.trademe.listings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.org.trademe.listings.data.model.Listing
import com.org.trademe.listings.domain.MockLatestListingsUseCase
import com.org.trademe.listings.presentation.LatestListingsViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val sampleListings = listOf(
        Listing(
            id = "1",
            title = "iPhone 14 Pro Max",
            region = "Auckland",
            photoUrls = listOf("https://example.com/iphone.jpg"),
            priceDisplay = "$1200.00",
            buyNowPrice = "$1100.00",
            isClassified = true
        ),
        Listing(
            id = "2",
            title = "Mountain Bike",
            region = "Wellington",
            photoUrls = listOf("https://example.com/bike.jpg"),
            priceDisplay = "$750.00",
            buyNowPrice = null,
            isClassified = false
        )
    )

    private lateinit var mockUseCase: MockLatestListingsUseCase
    private lateinit var viewModel: LatestListingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk<MockLatestListingsUseCase>()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `viewModel initializes and calls use case`() = runTest {
        // Given
        coEvery { mockUseCase.getList() } returns Result.success(sampleListings)

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { mockUseCase.getList() }
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertNull(uiState.error)
    }

    @Test
    fun `success state updates UI correctly`() = runTest {
        // Given
        coEvery { mockUseCase.getList() } returns Result.success(sampleListings)

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertEquals("iPhone 14 Pro Max", uiState.listings[0].title)
        assertEquals("Mountain Bike", uiState.listings[1].title)
        assertEquals("$1200.00", uiState.listings[0].priceDisplay)
        assertEquals("Auckland", uiState.listings[0].region)
        assertNull(uiState.error)
    }

    @Test
    fun `error state handles network failure`() = runTest {
        // Given
        val errorMessage = "Network connection failed"
        val exception = Exception(errorMessage)
        coEvery { mockUseCase.getList() } returns Result.failure(exception)

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
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
        coEvery { mockUseCase.getList() } returns Result.success(emptyList())

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertTrue(uiState.listings.isEmpty())
        assertNull(uiState.error)
    }

    @Test
    fun `retry calls use case again after error`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { mockUseCase.getList() } returns Result.failure(exception) andThen Result.success(
            sampleListings
        )

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify initial error state
        assertTrue(viewModel.uiState.value.error != null)
        assertTrue(viewModel.uiState.value.listings.isEmpty())

        // When retry is called
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { mockUseCase.getList() }
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertNull(uiState.error)
    }

    @Test
    fun `handles empty photoUrls list gracefully`() = runTest {
        // Given
        val listingsWithEmptyPhotos = listOf(
            Listing(
                id = "3",
                title = "Product with no photos",
                region = "Christchurch",
                photoUrls = emptyList(),
                priceDisplay = "$500.00",
                buyNowPrice = null,
                isClassified = true
            )
        )
        coEvery { mockUseCase.getList() } returns Result.success(listingsWithEmptyPhotos)

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(1, uiState.listings.size)
        assertEquals("Product with no photos", uiState.listings[0].title)
        assertTrue(uiState.listings[0].photoUrls.isEmpty())
        assertEquals("$500.00", uiState.listings[0].priceDisplay)
        assertNull(uiState.listings[0].buyNowPrice)
        assertTrue(uiState.listings[0].isClassified)
        assertNull(uiState.error)
    }

    @Test
    fun `multiple retry calls work correctly`() = runTest {
        // Given
        coEvery { mockUseCase.getList() } returns Result.success(sampleListings)

        // When
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 3) { mockUseCase.getList() }
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertEquals(2, uiState.listings.size)
        assertNull(uiState.error)
    }

    @Test
    fun `error message is null when request succeeds after failure`() = runTest {
        // Given
        val exception = Exception("Initial error")
        coEvery { mockUseCase.getList() } returns Result.failure(exception)
        viewModel = LatestListingsViewModel(mockUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify error state
        assertEquals("Initial error", viewModel.uiState.value.error)

        // When success result is returned on retry
        coEvery { mockUseCase.getList() } returns Result.success(sampleListings)
        viewModel.retry()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.error)
        assertEquals(2, uiState.listings.size)
        assertFalse(uiState.isLoading)
    }
}