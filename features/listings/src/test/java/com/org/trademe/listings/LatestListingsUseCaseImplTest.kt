package com.org.trademe.listings

import com.org.trademe.listings.data.model.Listing
import com.org.trademe.listings.data.repository.ListingsRepository
import com.org.trademe.listings.domain.LatestListingsUseCaseImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LatestListingsUseCaseImplTest {

    private lateinit var repository: ListingsRepository
    private lateinit var useCase: LatestListingsUseCaseImpl
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        repository = mockk()
        useCase = LatestListingsUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getList returns success from repository`() = testScope.runTest {
        // Given
        val expectedListings = listOf(
            Listing(
                id = "1",
                title = "Test Listing 1",
                region = "Auckland",
                photoUrls = listOf("photo1.jpg", "photo2.jpg"),
                priceDisplay = "$500",
                buyNowPrice = "$450",
                isClassified = false
            ),
            Listing(
                id = "2",
                title = "Test Listing 2",
                region = "Wellington",
                photoUrls = listOf("photo3.jpg"),
                priceDisplay = "$200",
                isClassified = true
            )
        )
        coEvery { repository.getLatestListings() } returns Result.success(expectedListings)

        // When
        val result = useCase.getList()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedListings, result.getOrNull())
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList returns empty list success from repository`() = testScope.runTest {
        // Given
        val emptyList = emptyList<Listing>()
        coEvery { repository.getLatestListings() } returns Result.success(emptyList)

        // When
        val result = useCase.getList()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList handles error state from repository`() = testScope.runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { repository.getLatestListings() } returns Result.failure(exception)

        // When
        val result = useCase.getList()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList handles exception from repository`() = testScope.runTest {
        // Given
        val exception = RuntimeException("Unexpected error")
        coEvery { repository.getLatestListings() } throws exception

        // When & Then
        try {
            useCase.getList()
        } catch (e: RuntimeException) {
            assertEquals("Unexpected error", e.message)
        }
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList calls repository exactly once`() = testScope.runTest {
        // Given
        val listings = listOf(
            Listing(
                id = "1",
                title = "Test Listing",
                region = "Christchurch",
                photoUrls = listOf("test.jpg"),
                priceDisplay = "$100"
            )
        )
        coEvery { repository.getLatestListings() } returns Result.success(listings)

        // When
        useCase.getList()

        // Then
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList with classified listings`() = testScope.runTest {
        // Given
        val classifiedListings = listOf(
            Listing(
                id = "1",
                title = "Classified Car",
                region = "Hamilton",
                photoUrls = listOf("car1.jpg"),
                priceDisplay = "$15,000",
                buyNowPrice = null,
                isClassified = true
            )
        )
        coEvery { repository.getLatestListings() } returns Result.success(classifiedListings)

        // When
        val result = useCase.getList()

        // Then
        assertTrue(result.isSuccess)
        val data = result.getOrNull()!!
        assertEquals(1, data.size)
        assertTrue(data[0].isClassified)
        assertEquals(null, data[0].buyNowPrice)
        coVerify(exactly = 1) { repository.getLatestListings() }
    }

    @Test
    fun `getList propagates repository failure result`() = testScope.runTest {
        // Given
        val networkException = Exception("No internet connection")
        coEvery { repository.getLatestListings() } returns Result.failure(networkException)

        // When
        val result = useCase.getList()

        // Then
        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { repository.getLatestListings() }
    }
}