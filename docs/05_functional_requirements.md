# 05 — Functional Requirements

## Entities Overview

Roundhouse manages:

1. Collector profile
2. Scales
3. Railway companies
4. Manufacturers
5. Railway models
6. Rolling stock
7. Collection and collection items
8. Wish lists and wish list items
9. Sellers
10. Favourites (shops, scales, companies, eras)
11. Consists
12. Maintenance cards and events

## Key Features

### Railway Model Management

- Create/edit/delete models
- Set manufacturer, product code, name, category, scale
- Add multiple rolling stock items per model
- Store short and detailed descriptions

### Rolling Stock

- Category-specific fields (locomotive, passenger car, freight car, EMU)
- Railway company, era, livery, road number
- Technical data: DCC readiness, socket type, coupler type, min radius, materials

### Collection Management

- Add models to collection with purchase metadata (date, price, currency, seller, condition, quantity)
- Edit/remove items
- View collection summaries and statistics

### Depot (Owned Rolling Stock)

- View all owned rolling stock (across models)
- Filter by category, DCC, company, era
- Quick access to maintenance/digitalization details

### Wish Lists

- Create/delete wish lists
- Add/remove models with target price, seller, priority
- Move items from wish list to collection

### Consists

- Create/edit/delete consists (train formations)
- Add, remove and reorder rolling stock in a consist
- Duplicate and search consists

### Maintenance

- Maintenance Card per owned unit: status, last/next date, decoder details
- Maintenance events (cleaning, inspection, lubrication, repair, upgrade)

### Google Drive Backup & Restore

- The app provides a robust backup and restore system for the local Room database and user images, supporting both Android and Desktop (JVM).
- Backups are bundled as compressed archives (e.g., .zip) containing the database files (.db, .db-shm, .db-wal) and images directory.
- Backups are stored in the user's Google Drive appDataFolder (hidden, not user-accessible) using the Google Drive REST API.
- Each backup includes schema version and timestamp metadata for safe restoration and migration.
- On cold start, if the local database is empty, the app checks Google Drive for available backups and offers restoration if compatible.
- Version checks ensure only compatible backups are restored; users are prompted to update the app if a backup is from a newer version.
- The restore process is atomic: all database files are replaced after closing Room connections, and Room migrations are run as needed.
- Users can configure backup frequency (Manual, Daily, Weekly). Android uses WorkManager; Desktop runs sync on startup or via coroutine.

#### Security Requirements
- OAuth2 with PKCE is used for authentication; only the drive.appdata scope is requested for least privilege.
- Tokens are stored securely: EncryptedSharedPreferences (Android) or native system vault (Desktop).
- Local database uses SQLCipher for encryption; backup archives are encrypted before upload using hardware-backed keys.
- All network traffic uses TLS 1.3; SHA-256 integrity checks are performed after download.
- Quota and network errors are handled with user notifications and retry logic.

#### Implementation Notes
- Uses Ktor for networking, Room KMP for database, and multiplatform settings for secure storage.
- Never use .fallbackToDestructiveMigration() in production; all migrations must be explicit.
- Backup and restore flows are user-facing, with clear warnings and options to merge or overwrite data.

### Persistent User Settings

- Provide a personalized experience by remembering user preferences such as currency, measurement system, default gauge filter, theme mode, and sync settings.
- Use Jetpack DataStore for key-value persistence, ensuring asynchronous and reactive updates.
- Implement platform-specific storage paths for Android and Desktop to maintain consistency and reliability.
- Ensure type safety by exposing settings as Enums in the Repository layer, while storing raw data as Strings in DataStore.
- Support instant saving of settings to prevent data loss and ensure session continuity.

## Testing Strategy

Roundhouse uses a pragmatic, production-grade testing and architectural plan to ensure deterministic behavior, strict boundaries, and high regression confidence across Android and Desktop (JVM):

### 1. Domain-Specific Logic (commonTest-first)
- All business rules (scale conversion, inventory valuation, collection filtering) are implemented as pure Kotlin in :core:domain, free of platform dependencies.
- Tests are written in commonTest using kotlin.test + JUnit, with table-driven and precision-verified scenarios for conversions, valuation, and filtering.
- These tests run identically on Android and Desktop JVM.

### 2. Room & Persistence
- Room DAOs/entities are internal to :core:database and exposed only via interfaces.
- DAO and repository tests use in-memory Room databases (Room.inMemoryDatabaseBuilder) in jvmTest, verifying CRUD, constraints, transactions, and bulk import integrity.
- Export/import logic (JSON/CSV) is tested in commonTest with round-trip and in-memory DB scenarios.

### 3. Architectural Enforcement
- Strict unidirectional data flow: UI → ViewModel → UseCase → Repository → DAO. UI never sees Room.
- Architecture tests (JVM) verify module boundaries (e.g., :feature:* does not depend on :core:database).

### 4. Platform-Specific UI Testing
- Android: Compose UI Test and emulator-based CI, with snapshot tests for phone/tablet/landscape using WindowSizeClass.
- Desktop: JVM UI tests (runComposeUiTest) for desktop-specific interactions (right-click, keyboard shortcuts, window resizing).

### 5. Regression Confidence (CI/CD)
- GitHub Actions matrix builds run all tests on Android and Desktop (Linux, Windows, macOS).
- Pipeline stages: static checks, common/jvm/Android/desktop tests, emulator and headless runs.
- Screenshot testing (e.g., Paparazzi/Showkase) in CI to catch UI regressions.

### Key Benefits
- Same domain tests across platforms, JVM-based Room tests reused, platform UI differences caught early, and strong architectural guarantees.

All new features require appropriate tests before merging. This strategy ensures deterministic, maintainable, and high-quality releases for collectors on all supported platforms.

## Non-Functional Requirements

Performance
- Search & filter responses should return under 1 second for collections up to ~5,000 models on supported devices (typical mobile hardware and mid-range desktop).
- Initial app cold start target: <3s on modern Android devices; warm start/app resume should be <500ms.
- Memory usage should be reasonable for mobile devices (avoid large in-memory caches); prefer streaming/pagination for large result sets.
- Background tasks (indexing, seed data migration) must be cancellable and yield-friendly.

Reliability & Data Integrity
- Use transactional database operations and Room migrations to maintain DB integrity across versions.
- All writes should be durable: commit to local SQLite and surface error states to the user when operations fail.
- Provide idempotent seed data application on startup (already specified) and verify checksums or version tokens during migrations.
- Automatic periodic integrity checks and lightweight DB repair strategies where possible; graceful degradation if corruption is detected (exportable diagnostics before destructive repair).

Usability & UX
- Follow Material Design 3 patterns for consistency across platforms (see `docs/03_product_overview.md` for theming guidance).
- Desktop should provide keyboard shortcuts and multi-window friendliness where appropriate.
- Provide discoverable affordances for common collection tasks (add, edit, search, import/export).
- Offline-first: the UI must function without network and indicate sync status when offline/online.

Maintainability & Code Quality
- Modular architecture (domain/repository/room/ui) with clear module boundaries and small public APIs.
- Prefer smaller, focused classes and functions; document public interfaces and complex flows.
- Use static analysis and linting (Kotlin/Compose linters and detekt) as part of CI.
- Maintain an automated test suite (unit + UI/snapshot tests) and require passing tests for release branches.

Security & Privacy
- Data is local-only by default; do not transmit personal data without explicit user consent.
- Respect OS-level file permissions and storage location conventions (see `docs/03_product_overview.md`).
- Encrypt exported backups if they contain personal data; consider offering optional on-device encryption for the database.
- Sanitize and validate imported CSV/JSON content to avoid malformed or malicious input.

Accessibility
- Ensure UI elements are accessible: content descriptions for icons, proper semantics for Compose components, and focus navigation on desktop.
- Respect system font scaling and provide responsive layouts that adapt to larger fonts.
- Test with screen readers on Android and accessibility tools on desktop platforms; ensure color contrast meets WCAG AA targets (see theming guidance).

Internationalization & Localization
- Support i18n: all user-facing strings must be externalized and translated; start with English and provide framework for additional locales.
- Use ICU-style formatting for dates, numbers, currencies, and pluralization.
- Ensure layouts accommodate long translations and right-to-left (RTL) where applicable.


Scalability & Extensibility
- Design data models and UI lists with pagination/virtualization to handle large collections beyond initial targets.
- Provide clear extension points for future features (sync providers, cloud backup, plugin-like exporters/importers).


Testing & QA
- Unit tests for domain and repository logic covering edge cases (empty collections, large data sets, DB migrations).
- UI snapshot tests for critical screens in light/dark themes and multiple sizes.
- Integration tests for data migration and backup/restore flows.
- Add automated contrast checks and accessibility audits in CI where feasible.

Observability & Logging
- Maintain structured, leveled logging for debugging and QA (INFO/WARN/ERROR) with toggles for verbose logs in debug builds only.
- Provide a simple app diagnostics export (logs + DB metadata + version) to help triage user issues without exfiltrating private data.


Backup & Recovery
- Provide a clear user-facing backup and restore flow with warnings on overwriting data and options to merge where sensible.

Deployment & Packaging
- Android packaging: produce AAB with appropriate ABI and resource optimizations.
- Desktop: provide native installers or bundles for Windows (MSI/EXE), macOS (DMG/PKG), and Linux (AppImage/DEB/RPM where feasible).
- Include release notes and migration instructions with each release when DB migrations occur.
