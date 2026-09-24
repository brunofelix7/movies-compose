# Moises Android Challenge - AI Agent Rules

This file defines the strict rules and workflows that any AI agent must follow when contributing to this project. 
These rules work in conjunction with the project's global skills (`android-architecture`, `android-data-layer`, `android-domain-layer`, `android-presentation-layer`, `android-unit-tests`, `android-ui-tests`).

## 1. Mandatory Test-Driven Generation (TDD)

Based on the project's existing high test coverage, you **MUST NEVER** create a new feature, component, or logic class without immediately creating its corresponding test file.

When you generate or modify any of the following file types, you are **OBLIGATED** to automatically generate the appropriate Unit Test (`src/test`) or UI Test (`src/androidTest`):

### Data & Domain Layers (Unit Tests)
*   **Repositories** (`*RepositoryImpl.kt`)
*   **Data Sources** (`*LocalDataSourceImpl.kt`, `*RemoteDataSourceImpl.kt`)
*   **API Services / DAOs** (`*Api.kt`, `*Dao.kt`)
*   **Mappers** (`*DtoMapper.kt`, `*EntityMapper.kt`, `*Mapper.kt`)
*   **Use Cases** (`*UseCase.kt` / `*UseCaseImpl.kt`)
*   **Utils & Extensions** (e.g., `*Ext.kt`, Utility classes)
*   **Databases** (`*Database.kt`)
*   *Rule:* Use the `android-unit-tests` skill (Kotest `DescribeSpec` + MockK).

### Presentation Layer (Unit Tests & UI Tests)
*   **ViewModels** (`*ViewModel.kt`) -> Requires Unit Test testing `UiState` and `UiEvent`.
*   **Screens** (`*Screen.kt`) -> Requires UI Test (JUnit4 + ComposeTestRule) testing the stateless component passing dummy `UiState`.
*   **Reusable Components** (e.g., `*Item.kt`, `*Bar.kt`, `*Card.kt` in `presentation/components`) -> Requires UI Test to ensure rendering and click listeners work.
*   **Navigation / Graph** (`*Graph.kt`) -> Requires UI Test to ensure routes render the correct screens.
*   **Controllers** (e.g., `*ControllerImpl.kt`) -> Requires UI or Unit tests depending on framework dependencies (like ExoPlayer).
*   *Rule:* Use the `android-ui-tests` skill for Compose UI elements, and `android-unit-tests` for ViewModels.

## 2. Execution Workflow

When a user asks you to "Create a new X feature":
1. Generate the Domain models and Use Cases. -> **Generate Use Case Unit Tests.**
2. Generate the Data layer (DTOs, Entities, Mappers, Data Sources, Repositories). -> **Generate Mapper, DataSource, and Repository Unit Tests.**
3. Generate the Presentation layer (ViewModels, Screens, Components). -> **Generate ViewModel Unit Tests and Screen/Component UI Tests.**
4. Never consider a task "Done" unless the corresponding tests have been successfully written.
