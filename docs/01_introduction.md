# 01 — Introduction

## Purpose

Roundhouse is a Kotlin Multiplatform (KMP) application designed for model railway collectors. It enables users to maintain a detailed, structured record of their model railway collection, wish lists, train consists, maintenance data, and catalogue details.

This document set defines the functional, non-functional, UX and data requirements for Roundhouse and serves as the single source of truth for specification and implementation decisions.

## Scope

Roundhouse provides:

- A single personal collection
- Multiple wish lists
- Detailed model and rolling stock definitions
- Purchase tracking for collection items
- Maintenance management (status, history, digital setup)
- Consist (train formation) composition
- Comprehensive search and filtering
- Import/export to CSV/JSON
- Local storage via SQLite (+ Room on supported platforms)

Target platforms: Android and Desktop (Windows, macOS, Linux).

## Out of Scope (v1)

- Cloud synchronization
- Online marketplace integration
- Multi-user accounts
- Automatic online catalogue ingestion
