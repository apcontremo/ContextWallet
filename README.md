# ContextWallet
<<<<<<< HEAD

A sophisticated Android application that manages documents based on temporal and spatial validity, with automatic reminders and intelligent state management.

## Features

### Core Functionality
- **Time-based Validation**: Documents are automatically activated and deactivated based on start and end dates
- **Location-based Validation**: Documents become active only when you're within a specified radius of validation points
- **Smart Reminders**: Automatic notifications 24 hours and 12 hours before a document becomes active
- **Three-state Management**:
  - **Active**: Currently valid (correct time and location)
  - **Inactive**: Either not yet started or outside validation range
  - **Expired**: Past the end date

### Document Management
- Import documents from other apps (images, PDFs, etc.)
- Set custom validation points with configurable radius
- Global mode for worldwide validity
- Track usage history
- Clean, minimalist Material 3 UI

## Technical Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room for local persistence
- **Background Tasks**: WorkManager for reliable reminder scheduling
- **Location Services**: Google Play Services Location API
- **Dependency Injection**: Hilt
- **Async Operations**: Kotlin Coroutines & Flow

## Project Structure

```
app/src/main/java/com/contextwallet/
├── data/
│   ├── local/
│   │   ├── entity/          # Room entities
│   │   ├── dao/             # Data access objects
│   │   └── AppDatabase.kt   # Database configuration
│   └── repository/          # Repository pattern
├── domain/
│   ├── model/               # Domain models
│   └── usecase/             # Business logic
├── ui/
│   ├── screens/             # Composable screens
│   ├── components/          # Reusable UI components
│   ├── navigation/          # Navigation setup
│   └── theme/               # Material 3 theme
├── workers/                 # Background workers
├── util/                    # Utility classes
└── di/                      # Dependency injection modules
```

## Key Components

### State Calculation Logic
Documents transition between states based on:
1. **Expired**: `currentDate > endDate`
2. **Inactive (Not Started)**: `currentDate < startDate`
3. **Inactive (Out of Range)**: In date range but outside all validation points
4. **Active**: In date range AND within radius of at least one validation point

### Reminder System
- Reminders are scheduled when a document is created
- Only future reminders are scheduled (if document is added late)
- Uses WorkManager for reliable background execution
- Notifications include document name and time until activation

### Location Tracking
- Uses Haversine formula for accurate distance calculations
- Supports multiple validation points per document
- Configurable radius per point
- Optional global mode for worldwide validity

## Permissions Required

- **Location**: `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
- **Notifications**: `POST_NOTIFICATIONS` (Android 13+)
- **Storage**: `READ_MEDIA_IMAGES`, `READ_MEDIA_DOCUMENTS`

## Building the Project

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on an emulator or physical device (Android 8.0+)

```bash
./gradlew build
```

## Running the App

```bash
./gradlew installDebug
```

## Future Enhancements

- [ ] Document detail screen with full preview
- [ ] Map view for adding/editing validation points
- [ ] Country/continent mode implementation with geocoding
- [ ] Export/import document configurations
- [ ] Widgets for quick document access
- [ ] Backup and restore functionality
- [ ] Dark mode optimization

## Play Store Preparation

- Package name: `com.contextwallet` (no personal identifiers)
- Minimum SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)
- Privacy policy required for location data usage
- Background location justification needed

## License

This project is ready for publication on Google Play Store.

## Author

Created as a document management solution with intelligent context awareness.
=======
A contextual wallet for documents you want to see "now" and "here"
>>>>>>> b79f778c5f43688441a73f14fccbbd39d312615b
