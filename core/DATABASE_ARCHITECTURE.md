# Database Architecture

## Overview

This project uses **Room Database** with a **feature-modular architecture**:
- **Core Module**: Provides Room dependencies and shared utilities
- **Feature Modules**: Each feature has its own database with feature-specific entities

## Architecture Principles

### ✅ What's in Core Module
```
core/
├── build.gradle.kts            # Room dependencies
└── src/main/java/.../database/
    ├── DatabaseConstants.kt    # Shared constants
    └── DatabaseCallback.kt     # Base callback class
```

**Core Module Provides:**
- Room dependencies (runtime, ktx, compiler)
- Shared database utilities and constants
- Base classes that features can extend
- No actual database implementation (avoids circular dependencies)

### ✅ What's in Feature Modules
```
features/todo/
├── build.gradle.kts            # Room dependencies
└── src/main/java/.../todo/
    ├── data/
    │   ├── local/
    │   │   ├── TodoEntity.kt        # Room Entity
    │   │   ├── TodoDao.kt           # Data Access Object
    │   │   └── TodoDatabase.kt      # Feature Database
    │   ├── source/
    │   │   ├── TodoLocalDataSource.kt
    │   │   └── TodoLocalDataSourceImpl.kt
    │   └── repository/
    │       └── TodoRepositoryImpl.kt
    └── di/
        └── DatabaseModule.kt        # Provides database & DAO
```

**Feature Modules Provide:**
- Feature-specific entities (e.g., `TodoEntity`)
- Feature-specific DAOs (e.g., `TodoDao`)
- Feature-specific database (e.g., `TodoDatabase`)
- Local data sources
- Repository implementations
- DI modules for their database components

## Why This Architecture?

### ❌ **Why NOT a Single Shared Database?**

```kotlin
// DON'T DO THIS - Creates circular dependencies
@Database(
    entities = [TodoEntity::class, UserEntity::class, ...],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao       // From todo module
    abstract fun userDao(): UserDao       // From user module
}
```

**Problems:**
- Core depends on feature modules (to know about entities)
- Feature modules depend on core (for base classes)
- Circular dependency! 💥
- Every feature change requires core module rebuild
- Tight coupling between unrelated features

### ✅ **Our Solution: Feature-Specific Databases**

```kotlin
// In todo module
@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

// In user module (example)
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

**Benefits:**
- ✅ No circular dependencies
- ✅ Features are independent
- ✅ Parallel development possible
- ✅ Easy to test features in isolation
- ✅ Feature can be removed without affecting others
- ✅ Each database can have its own version/migration

## Implementation Pattern

### Step 1: Add Room Dependencies to Feature Module
```kotlin
// features/yourfeature/build.gradle.kts
dependencies {
    implementation(project(":core"))
    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)
}
```

### Step 2: Create Entity
```kotlin
@Entity(tableName = "your_table")
data class YourEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String
)
```

### Step 3: Create DAO
```kotlin
@Dao
interface YourDao {
    @Query("SELECT * FROM your_table")
    fun getAll(): Flow<List<YourEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: YourEntity)
}
```

### Step 4: Create Feature Database
```kotlin
@Database(entities = [YourEntity::class], version = 1)
abstract class YourDatabase : RoomDatabase() {
    abstract fun yourDao(): YourDao
    
    companion object {
        const val DATABASE_NAME = "your_database"
    }
}
```

### Step 5: Create DI Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideYourDatabase(
        @ApplicationContext context: Context
    ): YourDatabase {
        return Room.databaseBuilder(
            context,
            YourDatabase::class.java,
            YourDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    @Singleton
    fun provideYourDao(database: YourDatabase): YourDao {
        return database.yourDao()
    }
}
```

## Database Files Per Feature

Each feature module that needs persistence should have:

```
feature/
└── data/
    ├── local/
    │   ├── *Entity.kt       # Room entities
    │   ├── *Dao.kt          # Data access objects
    │   └── *Database.kt     # Feature database
    ├── source/
    │   ├── *LocalDataSource.kt       # Interface
    │   └── *LocalDataSourceImpl.kt   # DAO wrapper
    ├── mapper/
    │   └── *Mapper.kt       # Entity ↔ Domain conversion
    └── repository/
        └── *RepositoryImpl.kt  # Uses local data source
```

## Migration Strategy

Each feature database manages its own migrations:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todos ADD COLUMN priority INTEGER DEFAULT 0")
    }
}

Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME)
    .addMigrations(MIGRATION_1_2)
    .build()
```

## Testing

### Unit Tests
- Mock DAOs to test repositories
- Mock repositories to test use cases
- In-memory database for DAO tests

### Integration Tests
```kotlin
@get:Rule
var instantTaskExecutorRule = InstantTaskExecutorRule()

private lateinit var database: TodoDatabase
private lateinit var dao: TodoDao

@Before
fun setup() {
    database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        TodoDatabase::class.java
    ).allowMainThreadQueries().build()
    
    dao = database.todoDao()
}
```

## Current Implementation

### Todo Feature ✅
- **Database**: `TodoDatabase`
- **Entity**: `TodoEntity` (id, title, description, isCompleted, createdAt)
- **DAO**: `TodoDao` (getAllTodos, insertTodo, updateTodo, deleteTodo)
- **Data Source**: `TodoLocalDataSourceImpl`
- **Repository**: `TodoRepositoryImpl` (uses local data source)

## Adding New Features

To add persistence to a new feature:

1. Add Room dependencies to feature's `build.gradle.kts`
2. Create entity classes with `@Entity`
3. Create DAO interface with `@Dao`
4. Create feature database with `@Database`
5. Create DI module to provide database & DAO
6. Create local data source implementation
7. Update repository to use local data source

## Best Practices

✅ **DO:**
- Keep entities in feature modules
- Use Flow for reactive queries
- Wrap operations in Result sealed class
- Use mappers to convert between layers
- Add proper indexes for frequently queried columns
- Write migration tests

❌ **DON'T:**
- Put entities in core module
- Use callbacks or LiveData (use Flow instead)
- Query database on main thread
- Expose entities directly to UI (use domain models)
- Skip migrations in production

## Benefits Summary

| Aspect | Benefit |
|--------|---------|
| **Modularity** | Features can be developed independently |
| **Testability** | Easy to test features in isolation |
| **Scalability** | Add features without touching core |
| **Maintainability** | Changes localized to feature modules |
| **Performance** | Multiple databases can run in parallel |
| **Deployment** | Can enable/disable features easily |

---

**Remember**: Room database is in core (as dependencies), but database implementations are in individual feature modules! 🚀

