# GitHub Copilot Instructions

## General instructions
- When working on tasks, check the documentation in the `docs/` folder and update it if your change affects behavior or usage.
- Ask for confirmation before cancelling or removing any existing code, unless explicitely requested

## Commits
- Use Conventional Commits for all commit messages
    - fix:      Bug fix (correlates with PATCH in SemVer)
    - feat:     New feature (correlates with MINOR in SemVer)
    - docs:     Changes to documentation
    - style:    Changes that do not affect the meaning of the code (white-space, formatting, etc...)
    - refactor: Changes that neither fixe a bug nor add a feature
    - perf:     Changes that improve performance
    - test:     Adding or refactoring tests
    - build:    Changes that affect the build system or external dependencies (example scopes: pip, make)
    - ci:       Changes to CI config files and scripts (pipelines as code, GH actions, etc...)
    - chore:    Other changes that don't modify src or test files
    - revert:   Reverts a previous commit

## Coding Standards
- Write idiomatic Kotlin: leverage the type system.
- Use clear, descriptive names for variables, functions, and types.
- Prefer `val` bindings and avoid mutable state unless necessary.
- Use packages to organize code logically.
- Avoid star imports (for example `import com.example.*`). Import explicitly only the symbols you need.
- Put each Kotlin `class` or `interface` in its own file named after the type (for example `MyFeature.kt` for `class MyFeature`).
- Ensure the `package` declaration matches the directory structure (for example `package com.example.feature` for files under `src/main/kotlin/com/example/feature`).
- Document public items with kdoc comments.
