# CeresLimit

## Description

CeresLimit is an Android application that implements a navigation drawer pattern for intuitive app navigation. This project is part of the CERES development initiative.

## Features

*   Navigation Drawer implementation
*   Category management functionality
*   AndroidX support
*   Modern Android architecture components

## Prerequisites

*   Android Studio
*   JDK 11
*   Android SDK (minimum supported API level not specified)
*   Gradle

## Setup Instructions

1.  **Clone the Repository**
    ```bash
    git clone [repository-url]
    cd DesarrolloCeresLimit-master
    ```
2.  **Open in Android Studio**
    *   Launch Android Studio
    *   Select "Open an existing project"
    *   Navigate to the cloned directory and select it

## Project Structure

*   `/app` - Main application module
*   `/gradle` - Gradle wrapper files
*   `.idea` - IntelliJ IDEA/Android Studio configuration files

## Build and Run

*   **Build the Application**
    ```bash
    # For Linux/macOS
    ./gradlew assembleDebug

    # For Windows
    gradlew.bat assembleDebug
    ```

*   **Run Tests**
    ```bash
    ./gradlew testDebugUnitTest
    ```

*   **Run Lint Checks**
    ```bash
    ./gradlew lint
    ```

## CI/CD

This project uses Bitbucket Pipelines for continuous integration. The pipeline is configured to:

*   Build the Android debug application
*   Run lint checks for code quality
*   Execute unit tests

The configuration can be found in `bitbucket-pipelines.yml`.

## Gradle Configuration

The project uses Gradle for dependency management with the following configurations:

*   AndroidX enabled
*   JetPack components
*   Memory allocation: `2048m` for JVM