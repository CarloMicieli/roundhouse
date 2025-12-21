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

### Import/Export

- Export collections and wish lists to CSV/JSON
- Optional CSV import for models in v1

### Persistent User Settings

- Provide a personalized experience by remembering user preferences such as currency, measurement system, default gauge filter, theme mode, and sync settings.
- Use Jetpack DataStore for key-value persistence, ensuring asynchronous and reactive updates.
- Implement platform-specific storage paths for Android and Desktop to maintain consistency and reliability.
- Ensure type safety by exposing settings as Enums in the Repository layer, while storing raw data as Strings in DataStore.
- Support instant saving of settings to prevent data loss and ensure session continuity.


## Testing Strategy

Roundhouse employs a comprehensive testing strategy to ensure reliability, maintainability, and high-quality user experience:

- **Unit Testing:** All domain and repository logic is covered by unit tests, focusing on edge cases such as empty collections, large datasets, and database migrations.
- **UI Snapshot Testing:** Critical screens are validated with snapshot tests across light/dark themes and multiple device sizes to catch regressions and visual inconsistencies.
- **Integration Testing:** Import/export, data migration, and backup/restore flows are verified with integration tests to ensure end-to-end correctness.
- **Accessibility & Contrast Audits:** Automated checks for color contrast and accessibility are included in CI pipelines where feasible.
- **Continuous Integration:** Static analysis, linting (Kotlin/Compose linters, detekt), and automated test suites are required to pass for all release branches.
- **Manual QA:** Key user flows are manually tested on supported platforms before major releases.

Test coverage and results are tracked as part of the release process, and new features must include appropriate tests before merging.

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
- Integration tests for import/export, data migration, and backup/restore flows.
- Add automated contrast checks and accessibility audits in CI where feasible.

Observability & Logging
- Maintain structured, leveled logging for debugging and QA (INFO/WARN/ERROR) with toggles for verbose logs in debug builds only.
- Provide a simple app diagnostics export (logs + DB metadata + version) to help triage user issues without exfiltrating private data.

Backup, Export & Recovery
- Support export/import of collections and wish lists to JSON/CSV (already specified); ensure exports include schema/version metadata for reliable imports.
- Provide a clear user-facing backup and restore flow with warnings on overwriting data and options to merge where sensible.

Deployment & Packaging
- Android packaging: produce AAB with appropriate ABI and resource optimizations.
- Desktop: provide native installers or bundles for Windows (MSI/EXE), macOS (DMG/PKG), and Linux (AppImage/DEB/RPM where feasible).
- Include release notes and migration instructions with each release when DB migrations occur.
