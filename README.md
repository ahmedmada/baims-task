# Weather App

A simple weather application in Android using Kotlin and modern development practices, including MVVM, Dependency Injection with Hilt, Coroutines, and Flow. This project uses mock data and includes unit tests for use cases.

## Features
- Fetch and display a list of cities
- Retrieve weather data for a selected city
- Offline support with cached data from local database
- Error handling and loading states

## Architecture
The project follows the MVVM (Model-View-ViewModel) architecture, with clean architecture principles for separating concerns. Key layers:
- **Domain**: Contains use cases, interfaces, and business logic.
- **Data**: Responsible for data handling (remote and local sources) and implements repository interfaces.
- **UI**: The user interface layer with activities/fragments observing ViewModels.

## Tech Stack
- **Kotlin**
- **Coroutines & Flow** for asynchronous data streams
- **Hilt** for dependency injection
- **Retrofit** for network requests
- **Room** for local data persistence
- **JUnit and Mockito** for unit testing
- **Mockk** for mocking dependencies in tests

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/ahmedmada/baims-task.git
    cd baims-task
    ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files to download all dependencies.

## Project Structure
- `data` - Contains models, repository implementations, local database (Room), and remote data source (Retrofit).
- `domain` - Contains use cases and repository interfaces.
- `ui` - UI components like activities, fragments, and ViewModels.
- `util` - Utility classes and helper functions, including `Resource` for managing data state (success, error, loading).

## Running the Tests
The project includes unit tests for use cases in the `domain` layer. These tests use `Mockito` and `kotlinx.coroutines.test` for coroutine testing.

### Prerequisites
- `JUnit`
- `Mockito`
- `kotlinx.coroutines.test`

### Running Tests
1. Run tests directly in Android Studio by navigating to the test file in `src/test/java`.
2. Alternatively, run tests using Gradle:
    ```bash
    ./gradlew test
    ```

### Example Test Cases
- **GetCitiesUseCaseTest**: Tests the city data retrieval use case with success and error scenarios.
- **GetWeatherDataUseCaseTest**: Tests the weather data retrieval use case with success and error scenarios.

## Example Code

### GetCitiesUseCase
```kotlin
class GetCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(): Flow<Resource<List<City>>> {
        return repository.getCities()
    }
}
