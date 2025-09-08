
# TradeMe Mobile App

A modern Android application for TradeMe built with Jetpack Compose, following clean architecture principles and MVVM design pattern.

## Features

- **Latest Listings**: Browse the most recent listings with image previews
- **Watchlist**: Save and monitor favorite listings
- **My TradeMe**: User profile management and account information
- **Search**: Find specific listings (coming soon)
- **Shopping Cart**: Purchase items directly (coming soon)

## Architecture

The app follows Clean Architecture principles with these distinct layers:

### Data Layer
- **Repositories**: Responsible for data operations and caching strategies
- **Remote Data Sources**: API clients for fetching data from TradeMe services
- **Local Data Sources**: Room database for offline caching

### Domain Layer
- **Use Cases**: Business logic encapsulation
- **Models**: Domain-specific data models
- **Repository Interfaces**: Abstractions for data operations

### Presentation Layer
- **ViewModels**: UI state management and business logic coordination
- **Screens**: Compose UI components
- **Navigation**: Single-activity architecture with Compose Navigation

## Tech Stack

- **UI**: Jetpack Compose with Material 3 design
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Navigation**: Compose Navigation
- **Concurrency**: Kotlin Coroutines & Flow
- **Image Loading**: Coil AsyncImage
- **Testing**: JUnit, Mockito, Turbine

## Getting Started

### Prerequisites
- Android Studio Ladybug (2024.2.1) or newer
- JDK 17

### Setup
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or physical device

## Testing

Run unit tests:
```
./gradlew test
```
