# Rick and Morty

## General information
App allows user to download characters from Rick and Morty movie. On picture click user can check detailed data about specific character. App caches dowloaded data, so user can use it offline.

## Architecture:
- App divided into several layers: general Android framework (UI, ViewModel, Activities, Fragments) - app, data fetching/caching - data and common code - core.
- App is built using recommended Google architecture: UI - ViewModel - Repository (local and remote). Basic architecture pattern is MVVM, implemented by using Architecture components.
- Dependencies and build logic are written in Kotlin script and should be placed in buildSrc module, which improves readability/comprehensibility of custom build logic.

## What can be improved/added from technical perspective:
- More UI and integration tests

## How to build and install app
Installs the Debug build

    ./gradlew installDebug

## How to test
- Connect Android device or launch emulator

- Install and run instrumentation tests on connected devices

      ./gradlew connectedAndroidTest

- Run unit tests

      ./gradlew test
