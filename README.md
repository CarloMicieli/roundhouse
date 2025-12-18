# roundhouse

![license](https://img.shields.io/github/license/CarloMicieli/roundhouse)
![GitHub last commit](https://img.shields.io/github/last-commit/CarloMicieli/roundhouse)
[![main](https://github.com/CarloMicieli/roundhouse/actions/workflows/main.yml/badge.svg)](https://github.com/CarloMicieli/roundhouse/actions/workflows/main.yml)

The ultimate companion app for scale modelers and collectors

## How to run

```bash
  $ git clone https://github.com/CarloMicieli/roundhouse
  $ cd roundhouse
```

Common Gradle tasks you may find useful (run from the project root):

- Build everything:

```bash
./gradlew build
```

- Run unit tests:

```bash
./gradlew test
```

- Verify formatting (Spotless):

```bash
./gradlew spotlessCheck
```

- Run general verification (lint, checks, tests):

```bash
./gradlew check
```

- Android - build debug APK for the `composeApp` module:

```bash
./gradlew :composeApp:assembleDebug
```

- Android - build release APK (requires signing configuration):

```bash
./gradlew :composeApp:assembleRelease
```

- Android - install debug APK to a connected device (device required):

```bash
./gradlew :composeApp:installDebug
```

- Desktop - build a distribution for the current OS (Compose Desktop):

```bash
./gradlew :composeApp:packageDistributionForCurrentOS
```

- Desktop - run the desktop application (if configured in the module):

```bash
./gradlew :composeApp:run
```

Notes:
- Replace `:composeApp:` with a different module path if you run tasks for other modules.
- For Android install/run tasks you need a connected device or emulator.

## Contribution

Contributions are always welcome!

See [CONTRIBUTING.md](CONTRIBUTING.md) for ways to get started.

Please adhere to this project's [code of conduct](CODE_OF_CONDUCT.md).

## License

[Apache 2.0 License](https://choosealicense.com/licenses/apache-2.0/)

```
   Copyright 2026 Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
