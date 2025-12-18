# Contributing to roundhouse

Thank you for your interest in contributing! We welcome contributions of all kinds — bug reports, documentation fixes, tests, small improvements, and larger features. This document explains how to get started and describes the project's Code of Conduct.

## How to contribute

- Search existing issues before opening a new one. If you find a related issue, add a comment describing your use case or proposed fix.
- For bug reports, include a short description, steps to reproduce, expected vs actual behavior, and relevant logs or stack traces.
- For feature requests, describe the problem you want to solve, suggested API or UI, and any alternatives you considered.

## Working on changes

- Fork the repository and create a topic branch for your change.
- Keep changes focused and small; one change per pull request makes review faster.
- Run existing tests and add tests for new features or bug fixes.
- Write clear commit messages and follow the existing code style.

Important: For larger changes, architectural work, or big pull requests, please contact the maintainer before you start. Opening an issue or discussing a proposed design first helps avoid duplicated effort and ensures the change fits the project's roadmap.

## Pull request process

1. Create a branch from `main` (or the project's default branch).
2. Open a pull request with a clear title and description describing what the change does and why.
3. Link related issues in the PR description (e.g., "Fixes #123").
4. Address review comments and keep the branch up to date with the base branch.

We will review PRs as promptly as possible. Be responsive to review feedback — iterative improvements are normal.

## Coding style and tests

- Follow the project's existing coding conventions.
- Keep public APIs stable and document breaking changes when unavoidable.
- Add unit tests for any new behavior and run the test suite before opening a PR:

  ./gradlew test

Adjust commands for your module names if the project layout differs.

## Reporting security issues

If you discover a security vulnerability, please do not create a public issue. Instead, contact the maintainer privately (see repository maintainer contact in the project settings or the project website) so we can coordinate a fix and release.

## Communication

- Use GitHub issues and pull requests for public discussion about bugs and features.
- For design conversations or status updates, open an issue and tag the maintainer.

## Thank you

Thanks for helping make `roundhouse` better. If you have questions about contributing, open an issue or contact the maintainer before starting large work.
