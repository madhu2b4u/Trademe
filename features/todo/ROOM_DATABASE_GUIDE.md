# Room Database Implementation Guide

## Architecture Overview

This Todo feature implements local data persistence using Room Database following clean architecture principles:

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                      │
│                    (TodoViewModel.kt)                       │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                      Domain Layer                           │
│           (TodoUseCase.kt, TodoUseCaseImpl.kt)             │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                       Data Layer                            │
│                                                              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Repository (TodoRepositoryImpl.kt)                 │   │
│  └────────────────────┬────────────────────────────────┘   │
│                       │                                      │
│  ┌────────────────────▼────────────────────────────────┐   │
│  │  Local Data Source (TodoLocalDataSourceImpl.kt)    │   │
│  └────────────────────┬────────────────────────────────┘   │
│                       │                                      │
│  ┌────────────────────▼────────────────────────────────┐   │
│  │  Room DAO (TodoDao.kt)                             │   │
│  └────────────────────┬────────────────────────────────┘   │
│                       │                                      │
│  ┌────────────────────▼────────────────────────────────┐   │
│  │  Room Database (TodoDatabase.kt)                   │   │
│  │  Entity: TodoEntity.kt                             │   │
│  └────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## Components

### 1. Database Layer (`data/local/`)

#### TodoEntity.kt
- Room entity representing the database table structure
- Maps to the "todos" table
- Contains: id, title, description, isCompleted, createdAt

#### TodoDao.kt
- Data Access Object for database operations
- Provides CRUD methods with Flow for reactive updates
- Key methods:
  - `getAllTodos()`: Returns Flow<List<TodoEntity>>
  - `insertTodo()`, `updateTodo()`, `deleteTodo()`
  - Query methods for completed/pending counts

#### TodoDatabase.kt
- Abstract Room database class
- Defines database name and version
- Provides abstract method to access TodoDao
- Located in feature module (todo) for feature-specific persistence

### 2. Data Source Layer (`data/source/`)

#### TodoLocalDataSource (Interface)
- Abstracts database operations from repository
- Follows clean architecture by creating a layer between repository and DAO

#### TodoLocalDataSourceImpl
- Implementation using Room DAO
- Direct bridge to database operations

### 3. Mapper Layer (`data/mapper/`)

#### TodoMapper.kt
- Extension functions for converting between layers
- `TodoEntity.toDomainModel()`: Database → Domain
- `Todo.toEntity()`: Domain → Database
- Keeps domain models independent from database implementation

### 4. Repository Layer (`data/repository/`)

#### TodoRepositoryImpl
- Uses TodoLocalDataSource for persistence
- Wraps operations in Result sealed class
- Converts entities to domain models using mappers
- Provides Flow-based reactive updates

## Dependency Injection

### DatabaseModule.kt
Provides:
- `TodoDatabase`: Room database instance
- `TodoDao`: DAO from database
- `TodoLocalDataSource`: Local data source implementation

### RepositoryModule.kt
Binds:
- `TodoRepository` → `TodoRepositoryImpl`
- `TodoUseCase` → `TodoUseCaseImpl`

## Database Migrations

### Current Version: 1

The database currently uses `fallbackToDestructiveMigration()` for development.

### For Production:
Implement proper migrations when schema changes:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add migration SQL here
        database.execSQL("ALTER TABLE todos ADD COLUMN priority INTEGER DEFAULT 0")
    }
}

Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME)
    .addMigrations(MIGRATION_1_2)
    .build()
```

## Data Flow

### Reading Data:
1. ViewModel calls UseCase
2. UseCase calls Repository
3. Repository gets Flow from LocalDataSource
4. LocalDataSource queries DAO
5. DAO returns Flow<List<TodoEntity>>
6. Mapper converts to Flow<List<Todo>>
7. Repository wraps in Result.Success
8. UI observes and updates

### Writing Data:
1. ViewModel calls UseCase with data
2. UseCase validates and calls Repository
3. Repository converts domain model to entity
4. LocalDataSource calls DAO
5. DAO performs database operation
6. Room automatically updates all Flow observers

## Key Features

✅ **Reactive Updates**: Using Flow for automatic UI updates
✅ **Clean Architecture**: Proper separation of concerns
✅ **Type Safety**: Strong typing across all layers
✅ **Error Handling**: Result sealed class for operations
✅ **Testability**: Each layer can be tested independently
✅ **Dependency Injection**: All dependencies provided by Hilt

## Testing

### Unit Tests (Recommended)
- Test Repository with fake LocalDataSource
- Test UseCase with fake Repository
- Test ViewModel with fake UseCase

### Integration Tests
- Test DAO operations with in-memory database
- Test complete flow from Repository to Database

## Future Enhancements

1. **Remote Sync**: Add remote data source for cloud backup
2. **Conflict Resolution**: Handle sync conflicts
3. **Data Export**: Export todos to JSON/CSV
4. **Search**: Add full-text search using FTS
5. **Categories**: Add category/tag support
6. **Reminders**: Add notification support

