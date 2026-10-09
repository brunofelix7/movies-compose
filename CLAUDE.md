# Project Instructions - Claude & Cursor

This file is the primary instruction set for Claude in Cursor (and Claude Code). Every section is mandatory.

The architecture standards in `.cursor/rules/` are project-agnostic and always applied in Cursor. They use placeholders (`<basePackage>`, `AppTheme`, `AppFontFamily`, "the design source") that resolve to the values in the **Project Profile** below. If this file and a rule disagree, stop and ask the user instead of picking one.

> **Reusing in another project**: copy `.cursor/rules/` and `.cursor/templates/` unchanged (plus `.agents/` and `GEMINI.md` if you use Antigravity), copy this file, and rewrite only **Part A**. Part B is the same in every project.

| Rule | Covers |
|---|---|
| `android-engineer.mdc` | Kotlin style, banned legacy tech, MVI, Hilt, testing stack |
| `android-architecture.mdc` | Module layout, dependency rules, Gradle conventions |
| `android-domain-layer.mdc` | Models, repository interfaces, `fun interface` use cases, `Resource<T>` |
| `android-data-layer.mdc` | DTOs, entities, mappers, data sources, repositories |
| `android-di.mdc` | Hilt modules in `:core:data/di/` |
| `android-presentation-layer.mdc` | MVI (`UiState` / `UiAction` / `UiEvent`), Route vs Screen, previews, Paging3 |
| `android-navigation.mdc` | Navigation3 routes, NavEntries, graph wiring |
| `android-design-system.mdc` | Design tokens, no hardcoded values, foundational components |
| `android-base-components.mdc` | Canonical base classes (`Resource`, `UiState`, `BaseRemoteDataSource`, `BasePagingSource`, ...) and their templates in `.cursor/templates/` |
| `android-unit-tests.mdc` | Kotest `DescribeSpec` + MockK |
| `android-ui-tests.mdc` | JUnit 4 + `ComposeTestRule` |
| `git-commit.mdc` | Commit format (only when the user asks to commit) |
| `generate-release-notes.mdc` | Play Console release notes (only when asked) |

---

# Part A: Project-specific (rewrite per project)

This part records decisions the user already made for this project: the single-module layout (A2), how to treat legacy code (A3), navigation (A4), and the UI reference (A5). Where they adapt a rule, follow them; that is not a disagreement to stop for. Anything they don't cover follows the rules, and any other conflict still goes to the user.

## A1. Project Profile

| Key | Value |
|---|---|
| App | Movies Explorer: browse movies and TV shows from the [TMDB API](https://developer.themoviedb.org/docs) |
| `<basePackage>` | `dev.brunofelix.movies` (single `:app` module with that namespace; layers are packages, see A2) |
| Application ID / launch activity | `dev.brunofelix.movies` (`prod` flavor; `beta` adds the `.beta` suffix) / `dev.brunofelix.movies/.core.presentation.ui.MainActivity` |
| Build variant | Flavors `prod` and `beta`. Use `prodDebug` unless told otherwise; Gradle task names are in A7 |
| Theme composable (`AppTheme`) | `PMovieTheme` |
| Theme mode | Light and dark (`LightColorPalette` / `DarkColorPalette` in `Theme.kt`, chosen by `isSystemInDarkTheme()`). Both hold the same values and screens read `Colors` directly, so the app is dark in both modes. Add new color roles to both palettes |
| Font family token (`AppFontFamily`) | `urbanist` (Urbanist regular, medium, bold; private in `Typography.kt`) |
| Theme files (`:core:designsystem` tokens) | `core/presentation/ui/theme/`: `Colors.kt` (the rules' `Color.kt`), `Typography.kt` (the rules' `Type.kt`, exposes `appTypography`), `Shapes.kt`, `Theme.kt` |
| Color tokens | `object Colors` in `Colors.kt`: `blackPrimary`, `blackSecondary`, `white`, `redPrimary`, `lightGray`, `darkGray`, `darkRed`. Only `blackPrimary` is mapped into the color schemes (`primary`, `secondary`) |
| Spacing and shape tokens | `SmallSpacing` (4dp), `MediumSpacing` (8dp), `LargeSpacing` (16dp) and `appShapes` (`small` / `medium` / `large`: 4, 8, 16dp corners) in `Shapes.kt`. Naming of new spacings: see A3 |
| Design source / tokens doc | None: no Stitch, Figma, or `DESIGN.md` (see A5) |
| Foundational components (`:core:designsystem/components`) | In `core/presentation/ui/components/`: `CustomButton` (filled or outlined), `CustomSearchBar`, `SelectorChip`, `SectionCard`, `GradientBackground` (`AppGradient`, `SplashGradient`), `MainTopBar`, `SecondaryTopBar`, `DetailTopBar`, `DetailStatusLayout`, `DetailSkeleton`, `LoadingState`, `EmptyState`, `EmptyImage`, `PagingRetry`, `MovieInfoChip`, `MovieOverview`, `YouTubePlayer`; plus `Modifier.shimmerEffect` in `core/presentation/util/ShimmerEffect.kt` |
| Shared presentation components (`:core:presentation/components`) | Same folder, for components that take domain or UI models, `UiState`, `UiText`, or `MainNavKey`: `MediaCard`, `MediaSection`, `CastSection`, `DetailHeader`, `MovieGenderContainer`, `CategorySelector`, `ErrorLayout`, `CustomNavBar` |
| Project-specific stack | TMDB REST API through Retrofit + **Gson** (`converter-gson`, `@SerializedName` DTOs); DataStore Preferences (language); Paging 3; android-youtube-player (trailers); Timber; Splash Screen API. Kotlinx Serialization only serializes navigation keys: `MainNavKey` and the `MediaListCategory` enum it carries, a known exception to the no-annotations rule in `core/domain` |
| Secrets | `apiKey.properties` at the root (`API_KEY`, `BASE_URL`, `BASE_URL_IMAGE`, exposed through `BuildConfig`) is required to build and must never be committed. `keystore.properties` is optional (release signing) |
| Localization | English (`values/`, default), Portuguese (`values-pt-rBR/`), Spanish (`values-es/`). Every new string goes into all three. Error strings live in `strings_errors.xml`, which is where the template's `error_*` strings get merged |

## A2. Single-Module Layout

The rules describe Gradle modules; this project has only `:app`. Read each module in the rules as the package below (paths relative to `app/src/main/java/dev/brunofelix/movies/`; tests mirror them under `app/src/test/` and `app/src/androidTest/`):

| Module in the rules | Package here |
|---|---|
| `:app` | Root package (`MyApplication`) and `core/presentation/ui/` (`MainActivity`, `MainScreen`, `SplashScreen`) |
| `:core:domain` | `core/domain/` (`model`, `repository`, `use_case`, `util`, `mapper`) |
| `:core:data` | `core/data/` (`local`, `remote`, `repository`, `util`). New use case implementations go in `core/data/use_case/` |
| `:core:data/di/` | `core/di/` |
| `:core:presentation` | `core/presentation/` (`util`, `mapper`, `ui/model`, `navigation`, shared components in `ui/components/`) |
| `:core:designsystem` | `core/presentation/ui/theme/` plus the foundational components listed in A1 |
| `:feature:<name>` | `feature/<name>/presentation/`; grouped features nest, e.g. `feature/movie/detail/` |

What changes because of this:
- **Module dependencies become import rules**: `core.domain` never imports Android, data, or presentation classes, and a `feature` package never imports another feature, DTOs, entities, or Retrofit/Room types.
- **Gradle**: every dependency goes in `app/build.gradle.kts`, still through `libs.*`, under the comment groups that file already uses (`// AndroidX & Core`, `// Jetpack Compose`, `// DI (Hilt)`, ...). Skip the "create a module" and namespace steps in the rules' checklists; create packages instead.
- **Resources**: there is a single `R` (`dev.brunofelix.movies.R`); templates that import `<basePackage>.core.presentation.R` use it instead.
- **Data packages**: Room lives in `core/data/local/db/` (`dao`, `entity`, `mapper`, `converter`, `AppDatabase`) and DataStore in `core/data/local/preferences/`. Retrofit interfaces are named `*Service.kt` (`MovieService`, `TvShowService`) instead of `*Api.kt`, and B1's API tests apply to them.
- **Base components**: the template classes already exist in their catalog packages, some with small compatible extras such as `Resource.map`. The only one missing is `ObserveAsEvents`: copy its template the first time a screen needs it. `Route` and `NavigationViewModel` are replaced by the project's own navigation (A4).

## A3. Legacy Code

Code written before these rules diverges from them in the ways listed below. New code follows the rules, using the package mapping from A2. Legacy code is migrated only when a task substantially rewrites it or when the user asks:
- Small changes (bug fixes, tweaks) to a legacy class follow that class's current pattern. Don't refactor beyond the request.
- When a task rewrites or substantially extends a legacy class, bring that class and its tests up to the rules within the task, and say so in the summary.
- Broad migrations (every ViewModel to MVI, renaming tokens, moving every feature use case to `core`) happen only on request.

| Area | Legacy pattern | New code |
|---|---|---|
| Feature domain, data, and DI | Use case interface and `Impl` in the same file under `feature/<name>/domain/use_case/`; Hilt modules in `feature/<name>/di/`; `feature/search/` also has its own `domain/repository` and `data/repository` | Interfaces in `core/domain/use_case/`, implementations in `core/data/use_case/`, bindings in `core/di/` (`UseCaseModule`, `RepositoryModule`) |
| Presentation | MVVM: public ViewModel functions and several `StateFlow`s; some screens receive the ViewModel directly; an app-wide `UiEvent` bus in `core/presentation/util/UiEvent.kt` | MVI from `android-presentation-layer.mdc`: `UiAction`, a per-screen `UiEvent` channel, Route + stateless Screen |
| Unit tests | JUnit 4 + Truth with `MainDispatcherRule`, `InstantTaskExecutorRule` / `LiveDataTestUtil`, and hand-written fakes in `test_util/fake/` | Kotest `DescribeSpec` + MockK. The JUnit Platform and `junit-vintage-engine` are already configured, so both styles run. Reuse the factories in `test_util/factory/` |
| Design tokens | `dp` / `sp` literals in screens and components; `Colors.*` read directly even for colors that have a Material role | Tokens only. New spacings use the `spacing{X}` name; for 4, 8, and 16dp keep `SmallSpacing` / `MediumSpacing` / `LargeSpacing` until they are renamed, instead of adding duplicate `spacing4` / `spacing8` / `spacing16` |
| JSON | Gson DTOs (`@SerializedName`) | Also Gson: both Retrofit services use `GsonConverterFactory`, which ignores Kotlinx `@Serializable` / `@SerialName`. Moving DTOs to Kotlinx Serialization is a broad migration |

## A4. Navigation

The project keeps its own Navigation3 setup instead of the `Route` / `NavigationViewModel` templates. Treat these as the rules' equivalents:

| In the rules | Here |
|---|---|
| `Route` | `MainNavKey` (`core/presentation/navigation/MainNavKey.kt`); `MainNavKeyExt.kt` maps media to detail keys (`toDetailNavKey()`) |
| `NavigationViewModel` | `MainNavViewModel`: one back stack per top-level tab (`topLevelTabs`: Movies, TV Shows, Favorites, Releases), `navigateTo` (switches tab when given a tab, pushes otherwise), `popBackStack`, and the search overlay visibility. There is no `replaceCurrent` / `onReplace` |
| `NavigationGraph` in `:app` | `MainNavDisplay` (`core/presentation/navigation/`), hosted by `MainScreen` |
| `<feature>NavEntry` | `EntryProviderScope<NavKey>.<feature>Entry(...)` in `feature/<name>/presentation/navigation/<Feature>NavEntry.kt`, taking `onNavigate: (MainNavKey) -> Unit` and/or `onBack` (tab roots also take `paddingValues`) |

To add a screen: add its key to `MainNavKey`, create the entry, and call it inside `MainNavDisplay`'s `entryProvider`. Navigation UI tests (B1) target `MainNavDisplay`.

## A5. UI Reference

There is no design source. Don't use Stitch (the "Movies (Compose)" project there is outdated), Figma, or a `DESIGN.md` for this app. The reference is the current Compose code and the screenshots in `screenshots/` (shown in `README.md`).
- Changing an existing screen: keep its layout and visual language unless the request says otherwise.
- New screen, or a layout the request doesn't describe: ask the user for a description or reference image before implementing. Never guess a layout.
- Translate every value into a token from A1, never a literal. If none matches, add one following `android-design-system.mdc` and use it.

## A6. Visual Language

- Flat black canvas: screens sit on `GradientBackground` with `AppGradient` (`Colors.blackPrimary`). `SplashGradient` (black into `Colors.darkRed`) is reserved for the splash.
- `Colors.redPrimary` is the single accent: selected tab, primary buttons, loaders, favorite icon, info chip icons. Text is `Colors.white`; secondary text and outlines use `Colors.lightGray`; placeholders and image fallbacks use `Colors.darkGray`.
- All text uses Urbanist through `MaterialTheme.typography` (`appTypography`).
- Home tabs show horizontal rows of rounded poster cards (`MediaSection` / `MediaCard`) under `MainTopBar` (search and settings), with `CustomNavBar` at the bottom.
- Movie and TV show details follow an Apple TV+ style: a full-bleed backdrop with parallax, content grouped in `SectionCard`s, and a transparent `DetailTopBar` whose title fades in as the user scrolls (`scrollFraction`).

## A7. Domain and Build Notes

- **TMDB requests**: `RemoteInterceptor` adds `api_key` and `language` (from `LanguageRepository`) to every request; never add them per endpoint.
- **Pagination**: TMDB lists paginate by page number (`@Query("page")`, 20 items per page), so `BasePagingSource` applies. Keep `PAGE_SIZE` in sync as `android-presentation-layer.mdc` requires.
- **Favorites** are stored locally in Room (`MediaEntity`, `MediaDao`) through `MediaRepository`.
- **Languages**: `LanguageEnum` (`en`, `pt-BR`, `es`) drives both the TMDB `language` parameter and the app locale. Adding one means a new `values-*` folder and an entry in `res/xml/locales_config.xml`.
- **Gradle tasks**: because of the flavors, the Part B commands (B2 step 5 and B5) map to these:

| Part B | Here |
|---|---|
| `./gradlew assembleDebug` | Same (builds both flavors); `./gradlew assembleProdDebug` builds only `prod` |
| `./gradlew testDebugUnitTest` | `./gradlew testProdDebugUnitTest` |
| `./gradlew connectedDebugAndroidTest` | `./gradlew connectedProdDebugAndroidTest` |
| `./gradlew installDebug` | `./gradlew installProdDebug` |

---

# Part B: Standard workflow (same in every project)

## B1. Mandatory Test-Driven Generation (TDD)
You **MUST NEVER** create a new feature, component, or logic class without creating its test file in the same task. When you create or modify any of the following, you are **OBLIGATED** to write the matching Unit Test (`src/test`) or UI Test (`src/androidTest`):

### Data & Domain Layers (Unit Tests, `android-unit-tests.mdc`)
- **Repositories** (`*RepositoryImpl.kt`)
- **Use Cases** (`*UseCaseImpl.kt`)
- **Data Sources** (`*LocalDataSourceImpl.kt`, `*RemoteDataSourceImpl.kt`)
- **API Services / DAOs** (`*Api.kt`, `*Dao.kt`)
- **Mappers** (`*DtoMapper.kt`, `*EntityMapper.kt`, `*Mapper.kt`)
- **Utils & Extensions** (`*Ext.kt`, utility classes)
- **Databases** (`*Database.kt`)

### Presentation Layer (`android-unit-tests.mdc` + `android-ui-tests.mdc`)
- **ViewModels** (`*ViewModel.kt`) → Unit test covering `UiState` transitions and emitted `UiEvent`s.
- **Screens** (`*Screen.kt`) → UI test of the stateless Screen with mock `UiState`s, never the Route.
- **Reusable Components** (`*Item.kt`, `*Bar.kt`, `*Card.kt`, anything in `presentation/components` or `:core:designsystem/components`) → UI test for rendering and click callbacks.
- **Navigation** (`NavigationGraph.kt`, `*NavEntry.kt`) → UI test that each route renders the right screen.
- **Controllers** (`*ControllerImpl.kt`) → Unit or UI test depending on framework dependencies.

## B2. Execution Workflow
When asked to create a new feature, work in this order:
1. **Domain** (`:core:domain`): models, repository interfaces, use case `fun interface`s. Test any logic that lives here (e.g. extensions in `/util`).
2. **Data** (`:core:data`): DTOs/entities, mappers, data sources, repository and use case implementations, Hilt bindings in `:core:data/di/`. → Mapper, data source, repository, and use case tests.
3. **Presentation** (`:feature:<name>`): `UiState` / `UiAction` / `UiEvent`, ViewModel, Route + stateless Screen, components, previews. → ViewModel unit tests and Screen/component UI tests.
4. **Navigation**: add the `Route`, create `<feature>NavEntry`, wire it into `NavigationGraph` in `:app`. → Navigation UI test.
5. **Verify**: run `./gradlew assembleDebug` and `./gradlew testDebugUnitTest`. A task is never "done" until its tests exist and pass. If something can't be run (e.g. no device for `connectedDebugAndroidTest`), say so explicitly.

Before using Hilt, Navigation3, or Kotest in a module, check that it is already configured in `gradle/libs.versions.toml` and the module's `build.gradle.kts`. If it isn't, tell the user that setting it up is part of the task instead of silently mixing patterns.

## B3. Jetpack Compose Previews
- Every new or modified composable (screen or component) gets at least one `@Preview` with mock data, wrapped in the theme composable from the Project Profile.
- Stateless `<Feature>Screen`s get one preview per possible `UiState` (Loading, Success, Empty, Error).

## B4. Git Workflow
- **No automatic commits**: you MUST NEVER run `git commit` (or push) on your own. Only stage or commit when the user explicitly asks (e.g., "commit these changes").
- When asked to commit, follow `git-commit.mdc` (gitmoji + Conventional Commits, split by feature/layer).

## B5. Build Commands
- **Compile Debug APK**: `./gradlew assembleDebug`
- **Run Unit Tests**: `./gradlew testDebugUnitTest`
- **Run UI Tests**: `./gradlew connectedDebugAndroidTest`
- **Install & Run on Device**: `./gradlew installDebug`, then `adb shell am start -n <launch activity from the Project Profile>`.
