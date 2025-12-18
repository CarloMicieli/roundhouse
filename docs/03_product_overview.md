# 03 — Product Overview

## Architecture

Roundhouse is implemented as a Kotlin Multiplatform application with shared UI via Compose Multiplatform and shared business/data logic.

Key technologies:
- Compose Multiplatform for UI
- Material Design 3 (Material You) — use Compose Material3 components and theming where available
- Room (multiplatform) + SQLite for persistence
- Koin for dependency injection
- Navigation Compose for navigation (must support Kotlin Multiplatform / KMP)
- kotlinx.serialization for JSON seed data
- Kermit (Touchlab) for Kotlin Multiplatform logging — structured, levelled logging with platform backends

## Design & Theming (Material Design 3)

Provide a single, coherent theming strategy based on Material Design 3. The goal is to keep the visual language consistent across Android and desktop while respecting platform affordances.

Theming strategy
- Use Compose Material3 APIs (ColorScheme, Typography, Shapes) in shared UI code where possible.
- Expose a single `AppTheme` from the shared UI module that accepts a `colorScheme`, `typography`, and `isDark` flag.
- Keep actual platform differences in small platform-specific adapters (expect/actual or simple helpers).

Color tokens and naming
- Rely on semantic color roles (the M3 ColorScheme) rather than raw hex values in UI components. Key tokens include:
  - primary / onPrimary
  - secondary / onSecondary
  - tertiary / onTertiary
  - background / onBackground
  - surface / onSurface
  - surfaceVariant / onSurfaceVariant
  - outline
  - error / onError
- Define any app-specific semantic tokens (e.g., highlight, success, warning) in terms of the ColorScheme so components can be platform-agnostic.
- Centralize color definitions in one place (e.g., shared ui/theme/Color.kt) and reference tokens from composables.

Platform-specific fallbacks
- Android (preferred behavior):
  - On Android 12+ (SDK 31+), enable dynamic color (Material You) using dynamicLightColorScheme / dynamicDarkColorScheme and seed colors where available.
  - On older Android versions, fall back to a predefined (seed-based) light and dark ColorScheme.
- Desktop (Windows, macOS, Linux):
  - Do not rely on dynamic color; provide curated light and dark ColorSchemes tuned for desktop UI.
  - Respect the system dark/light preference by observing the platform appearance but offer an in-app theme override.
- Shared behavior:
  - Components should read semantic tokens only; platform adapters convert dynamic or static palettes into the shared ColorScheme.

Accessibility and contrast
- Ensure text and important UI elements meet contrast guidelines (WCAG AA):
  - Contrast ratio >= 4.5:1 for normal body text.
  - Contrast ratio >= 3:1 for large text (>= 18pt or 14pt bold).
- Provide sufficient contrast for disabled states, dividers, and icons.
- Test both light and dark schemes and with user font scaling.

Implementation notes (practical)
- Create a shared `AppTheme` composable in the common UI module that applies MaterialTheme(colorScheme, typography, shapes).
- Keep platform-specific code small:
  - Android: provide a ThemeAndroid.kt that chooses dynamic vs fallback schemes using context-aware APIs.
  - Desktop: provide a ThemeDesktop.kt with curated schemes and system appearance detection.
- Provide a small utility to convert a seed color to a ColorScheme (use Compose Material3's colorSchemeFromSeed or equivalent) so designers can iterate quickly.
- Avoid hard-coding colors in components; prefer parameters that accept Color from the theme or named tokens.

Testing & QA
- Add visual tests or snapshot tests for critical screens in both light and dark modes.
- Run contrast checks with automated tools or linters that validate token contrasts.

Migration tips
- When migrating existing Compose components, replace direct Color(...) usages with colorToken references from the theme.
- Prefer Material3 components (TopAppBar, NavigationBar, ElevatedButton, etc.) to benefit from built-in tokens and semantics.

## Target Platforms

- Android (AAB/APK)
- Desktop: Windows, macOS, Linux (packaged via Compose Desktop tooling)

## Data Storage Locations (platform conventions)

- Linux: ~/.local/share/Roundhouse (or $XDG_DATA_HOME/Roundhouse)
- Windows: %LOCALAPPDATA%\Roundhouse
- macOS: ~/Library/Application Support/Roundhouse
- Android: app private storage

## Data Seeding

The app ships with seed JSON for reference tables (scales, manufacturers, railway companies). Seed rules:
- Packaged in common resources
- Idempotent on startup: insert missing rows, update rows when seed `version` increments, ignore unchanged rows
- Stored using kotlinx.serialization format (JSON)

## Constraints

- Offline-first design for v1
- Local SQLite database, text UTF-8
- Must scale to collections up to ~5,000 models with <1s search/filter responses (target)
