# GitHub Copilot Instructions

## General instructions
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
