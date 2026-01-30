# KMP Demo Project

This project is a demonstration of a Kotlin Multiplatform (KMP) application, showcasing a modern, modular architecture for building apps targeting both Android and iOS from a single shared codebase.

## Setup Instructions

To get the project up and running on your local machine, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/kvothe321/KMP-clean-arch-demo.git
    ```
2.  **Open in Android Studio:**
    Open the cloned project in Android Studio.
3.  **JDK Configuration:**
    Ensure your IDE is configured to use JDK 11. The project is configured to use Java 11.

## Architecture Overview

The project follows a clean, multi-module architecture designed for scalability and separation of concerns.

### Module Structure

-   `composeApp`: The main application module for the Android platform. It integrates all the shared and feature modules. It can also be seen as Framework & Drivers layer 
-   `shared`: This is the core KMP module containing logic shared across all platforms. It is further divided into layers:
    -   `shared/core`: Contains business rules, business logic, models, repositories, and use cases that are platform-agnostic.
    -   `shared/data`: Implements the repository interfaces defined in the domain layer. It handles data fetching from remote (network) and local (database/datastore) sources.
-   `feature/*`: These are individual feature modules, each representing a specific screen or user flow (e.g., `products`, `productdetails`, `favorites`). They contain UI and state management logic (ViewModels) for that feature.
-   `build-logic`: This module contains custom Gradle convention plugins to standardize and simplify build configurations across all other modules, promoting consistency and reducing boilerplate.

### Architectural Layers

The architecture is layered to separate responsibilities:

1.  **UI Layer (`feature/*` modules):** Composable screens and UI components that observe state from ViewModels. Built with Jetpack Compose for Android and SwiftUI for ios. 
2.  **Presentation Layer (`feature/*` modules):** ViewModels that manage UI state, handle user events, and interact with use cases.
3.  **Domain Layer (`shared/core/domain`):** Contains the core business logic.
    -   **Use Cases:** Encapsulate specific business operations (e.g., `GetProductsUseCase`).
    -   **Repositories (Interfaces):** Define contracts for data operations (e.g., `ProductRepository`).
    -   **Models:** Represent the core data structures of the application.
4.  **Data Layer (`shared/data`):** Provides concrete implementations for the repository interfaces.
    -   **Repositories (Implementations):** Coordinate data from different data sources.
    -   **Data Sources:** Abstract the origin of data, whether it's a remote API or local storage.
    -   **Networking:** Ktor is used for making HTTP requests to a remote API.
    -   **Local Storage:** Jetpack DataStore is used for simple key-value storage, like managing favorite products.

### Dependency Injection

**Koin** is used for dependency injection throughout the application, simplifying the management of dependencies across different layers and modules.

## iOS Considerations

The shared code within the `shared` module is fully compatible with iOS. Here's how an iOS application would consume it:

### Framework Export

The Gradle build scripts are configured to compile the `shared` module into a native `.framework` for iOS.

### Consuming Shared Code in Swift

1.  **ViewModels:** ViewModels from the `feature` modules can be instantiated and used within a SwiftUI view. This project utilizes **SKIE** from Touchlab, which automatically generates Swift-friendly APIs for Kotlin code. SKIE bridges Kotlin's `StateFlow` into a Swift `AsyncSequence`, allowing you to observe state changes directly and asynchronously in SwiftUI using the `for await...in` syntax, eliminating the need for manual bridging or wrapper libraries.

2.  **Use Cases & Repositories:** The domain and data layers can be accessed directly from Swift if needed, allowing the iOS app to leverage all the shared business and data-handling logic.

3.  **Dependency Injection:** The Koin dependency graph can be initialized from the iOS application's entry point (`AppDelegate` or `App` struct), making all shared dependencies available to the iOS-specific code.

4.  **Platform-Specific APIs:** The `expect`/`actual` mechanism is used to provide platform-specific implementations where necessary (e.g., creating an `HttpClientEngine` or a `DataStore` instance). The iOS-specific `actual` implementations are located in the `iosMain` source set.
