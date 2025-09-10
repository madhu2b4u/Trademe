package com.org.trademe.listings.data.repository

import com.org.trademe.listings.data.model.Listing
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockListingsRepositoryImpl @Inject constructor() : MockListingsRepository {

    override suspend fun getLatestListings(): Result<List<Listing>> {
        // Simulate network delay
        delay(1000)

        val mockListings = listOf(
            Listing(
                id = "1",
                title = "2019 Toyota Camry - Excellent Condition",
                region = "Auckland",
                photoUrls = listOf("https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=400"),
                priceDisplay = "$25,000",
                buyNowPrice = "$28,000",
                isClassified = false
            ),
            Listing(
                id = "2",
                title = "MacBook Pro 16-inch M2 2023",
                region = "Wellington",
                photoUrls = listOf("https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400"),
                priceDisplay = "$3,200",
                isClassified = true
            ),
            Listing(
                id = "3",
                title = "iPhone 15 Pro Max 256GB - Like New",
                region = "Christchurch",
                photoUrls = listOf("https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400"),
                priceDisplay = "$1,800",
                buyNowPrice = "$2,000",
                isClassified = false
            ),
            Listing(
                id = "4",
                title = "Vintage Leather Sofa Set",
                region = "Hamilton",
                photoUrls = listOf("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400"),
                priceDisplay = "$850",
                isClassified = true
            ),
            Listing(
                id = "5",
                title = "Trek Mountain Bike - Carbon Frame",
                region = "Tauranga",
                photoUrls = listOf("https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400"),
                priceDisplay = "$2,400",
                buyNowPrice = "$2,800",
                isClassified = false
            ),
            Listing(
                id = "6",
                title = "Nikon D850 DSLR Camera Body",
                region = "Dunedin",
                photoUrls = listOf("https://images.unsplash.com/photo-1606983340126-99ab4feaa64a?w=400"),
                priceDisplay = "$1,900",
                isClassified = true
            ),
            Listing(
                id = "7",
                title = "Gaming PC - RTX 4080, i7-13700K",
                region = "Auckland",
                photoUrls = listOf("https://images.unsplash.com/photo-1593640408182-31c70c8268f5?w=400"),
                priceDisplay = "$3,500",
                buyNowPrice = "$4,000",
                isClassified = false
            ),
            Listing(
                id = "8",
                title = "Samsung 65\" OLED Smart TV",
                region = "Wellington",
                photoUrls = listOf("https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=400"),
                priceDisplay = "$2,200",
                isClassified = true
            ),
            Listing(
                id = "9",
                title = "Breville Barista Express Coffee Machine",
                region = "Palmerston North",
                photoUrls = listOf("https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=400"),
                priceDisplay = "$650",
                buyNowPrice = "$750",
                isClassified = false
            ),
            Listing(
                id = "10",
                title = "Weber BBQ Grill - Stainless Steel",
                region = "Napier",
                photoUrls = listOf("https://images.unsplash.com/photo-1544025162-d76694265947?w=400"),
                priceDisplay = "$450",
                isClassified = true
            ),
            Listing(
                id = "11",
                title = "PlayStation 5 Console + Controller",
                region = "Rotorua",
                photoUrls = listOf("https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=400"),
                priceDisplay = "$750",
                buyNowPrice = "$850",
                isClassified = false
            ),
            Listing(
                id = "12",
                title = "Dyson V15 Vacuum Cleaner",
                region = "New Plymouth",
                photoUrls = listOf("https://images.unsplash.com/photo-1558317374-067fb5f30001?w=400"),
                priceDisplay = "$680",
                isClassified = true
            ),
            Listing(
                id = "13",
                title = "Canon EOS R5 Mirrorless Camera",
                region = "Auckland",
                photoUrls = listOf("https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400"),
                priceDisplay = "$4,200",
                buyNowPrice = "$4,800",
                isClassified = false
            ),
            Listing(
                id = "14",
                title = "Vintage Gibson Les Paul Guitar",
                region = "Wellington",
                photoUrls = listOf("https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400"),
                priceDisplay = "$3,800",
                isClassified = true
            ),
            Listing(
                id = "15",
                title = "KitchenAid Stand Mixer - Red",
                region = "Christchurch",
                photoUrls = listOf("https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400"),
                priceDisplay = "$420",
                buyNowPrice = "$480",
                isClassified = false
            ),
            Listing(
                id = "16",
                title = "Office Desk Setup - Standing Desk",
                region = "Hamilton",
                photoUrls = listOf("https://images.unsplash.com/photo-1586953208448-b95a79798f07?w=400"),
                priceDisplay = "$580",
                isClassified = true
            ),
            Listing(
                id = "17",
                title = "Nintendo Switch OLED + Games",
                region = "Tauranga",
                photoUrls = listOf("https://images.unsplash.com/photo-1578303512597-81e6cc155b3e?w=400"),
                priceDisplay = "$450",
                buyNowPrice = "$520",
                isClassified = false
            ),
            Listing(
                id = "18",
                title = "DeLonghi Espresso Machine",
                region = "Queenstown",
                photoUrls = listOf("https://images.unsplash.com/photo-1610889556528-9a770e32642f?w=400"),
                priceDisplay = "$320",
                isClassified = true
            ),
            Listing(
                id = "19",
                title = "Instant Pot Duo 8Qt Pressure Cooker",
                region = "Invercargill",
                photoUrls = listOf("https://images.unsplash.com/photo-1585515656643-4a5b8e5e5b8a?w=400"),
                priceDisplay = "$180",
                buyNowPrice = "$220",
                isClassified = false
            ),
            Listing(
                id = "20",
                title = "Herman Miller Office Chair",
                region = "Auckland",
                photoUrls = listOf("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400"),
                priceDisplay = "$890",
                isClassified = true
            )
        )

        return Result.success(mockListings)
    }
}
