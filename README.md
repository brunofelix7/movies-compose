# Movies Explorer 🎬

**Movies Explorer** is a modern Android application that allows you to explore movies and TV shows using the [TMDB API](https://www.themoviedb.org/). The project was developed with a focus on current technologies, following the best practices for Android development.

---

## 📸 Screenshots

<div>
  <img src="screenshots/screenshot_01.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_02.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_03.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_04.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_05.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_06.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_07.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_08.png" width="30%" alt="screenshot" />
  <img src="screenshots/screenshot_09.png" width="30%" alt="screenshot" />
</div>

---

## 🏗️ Architecture

The project follows **Clean Architecture** with an **MVI (Model-View-Intent)** presentation layer, split into Gradle modules: shared domain and data logic live in `core` modules, and each `feature` module holds its presentation code plus the use cases only it needs.

- **`:core:domain`**: Pure Kotlin. Domain models, repository interfaces, and the Use Cases shared by several features.
- **`:core:data`**: Repositories, data sources (TMDB API with Retrofit, Room, DataStore), DTO and entity mappers, and the shared Hilt modules.
- **`:core:presentation`**: Base presentation classes (`UiState`, `UiText`, paging helpers), navigation routes, UI models, and shared components.
- **`:core:designsystem`**: Theme tokens (colors, typography, spacing, shapes) and foundational components.
- **`:feature:*`**: One module per feature (splash, movie, tv_show, favorite, release, search, settings, media_list), each with its ViewModels (`UiState` / `UiAction` / `UiEvent`), screens, and navigation entries, plus its own Use Cases (`domain`) and their Hilt bindings (`di`).
- **`:app`**: Entry point that wires the navigation graph and the app shell.

---

## 🛠️ Technologies and Dependencies

### Core
- **Kotlin**: Modern and concise programming language.
- **Coroutines & Flow**: Asynchronous programming and reactive data streams.
- **Hilt (Dagger)**: Dependency injection for Android.

### UI & UX
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Material 3**: Google's design system for modern interfaces.
- **Navigation 3**: Type-safe and decoupled navigation between screens.
- **Coil**: Efficient image loading optimized for Compose.
- **Splash Screen API**: Native support for splash screens.

### Data & Networking
- **Retrofit & OkHttp**: REST API consumption and HTTP request management.
- **Room**: Local database (SQLite) with robust abstraction.
- **Paging 3**: Efficient data pagination from both API and local database.
- **DataStore**: Secure and reactive preference storage.
- **Kotlinx Serialization**: JSON data serialization.

### Utilities
- **Timber**: Extensible logging for Android.

---

## 🧪 Testing
The project has a solid testing foundation to ensure code quality:
- **Unit Tests**: Kotest (`DescribeSpec`), MockK, Turbine, Paging Testing, and MockWebServer for the API contracts.
- **Instrumentation Tests**: Room DAO tests, Compose UI tests for every screen and component (JUnit 4 + Compose Test Rule with Kotest matchers), and a Hilt navigation test for the whole graph.

---

## 📝 Commit Patterns

The project adopts the **Conventional Commits** standard in conjunction with **Gitmojis** to maintain a readable and organized change history.

### Commit Types:
- `✨ feat`: Introduction of new features.
- `♻️ refactor`: Code changes that neither fix bugs nor add features.
- `✅ test`: Adding or correcting tests.
- `🔧 chore`: Maintenance tasks, build configurations, etc.
- `📦 build`: Changes affecting the build system or external dependencies.
- `📝 docs`: Documentation changes.

### Example:
`✨ feat(search): implement debounced movie search`

---

## 🚀 How to Run

1. Clone the repository.
2. Obtain an API key from [TMDB](https://www.themoviedb.org/documentation/api).
3. Create an `apiKey.properties` file in the root directory with the following keys:
   ```properties
   API_KEY=YOUR_API_KEY_HERE
   BASE_URL=https://api.themoviedb.org/3/
   BASE_URL_IMAGE=https://image.tmdb.org/t/p/
   ```
4. Sync Gradle and run the application.

---

Developed by [Bruno Félix](https://github.com/brunofelix) 🚀
