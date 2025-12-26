# Contributing to Synheart Behavior

Thank you for your interest in contributing to Synheart Behavior 🎉

Synheart Behavior is a privacy-first, on-device digital behavior signal SDK supporting multiple platforms, including Python, Flutter/Dart, Android/Kotlin, and iOS/Swift.

This document outlines how to contribute effectively while maintaining Synheart’s engineering standards, privacy guarantees, and research rigor.

## 🚀 Getting Started

1. Fork the repository on GitHub

2. Clone your fork locally:

```bash
git clone https://github.com/your-username/synheart-behavior.git
cd synheart-behavior
```

3. Review the documentation:
- ``docs/ARCHITECTURE.md``
- ``docs/API_REFERENCE.md``
- ``docs/PRIVACY.md``

## 📂 Repository Structure

This repository serves as the shared source of truth for all Synheart Behavior SDKs.

```
synheart-behavior/
├── docs/                  # Architecture, API, privacy guarantees
├── examples/              # Cross-platform usage examples
├── scripts/               # CI, build, release utilities
├── CONTRIBUTING.md        # Contribution guidelines
└── README.md              # Project overview
```

**Platform-Specific SDK Repositories**
Each SDK is implemented and maintained in its own repository:
- Python: ``synheart-behavior-python``
- Flutter/Dart: ``synheart-behavior-dart``
- Android/Kotlin: ``synheart-behavior-kotlin``
- iOS/Swift: ``synheart-behavior-swift``

Contributions may target either this repository (shared specs/docs) or a platform-specific SDK repo.

## 🧪 Development Setup by SDK

### Python SDK

```bash
cd synheart-behavior-python

pip install -r requirements.txt
pip install -r requirements-dev.txt

pytest
pytest --cov=src/synheart_behavior --cov-report=html
```

### Flutter / Dart SDK

```bash
cd synheart-behavior-dart

flutter pub get
flutter test
flutter test --coverage
```

### Android / Kotlin SDK

```bash
cd synheart-behavior-kotlin

./gradlew build
./gradlew test
```

### iOS / Swift SDK

```
cd synheart-behavior-swift

swift build
swift test
```

## 📝 Contribution Workflow

1. Create a feature or fix branch

```bash
git checkout -b feature/your-feature-name
```

2. Make your changes

3. Follow platform-specific conventions

- Preserve privacy guarantees
- Avoid collecting content or identifiers
- Add tests for new behavior

4. Run tests locally

- All tests must pass
- Performance regressions are not acceptable
- Commit with a clear message

```bash
git commit -m "Add: interaction burstiness metric"
```

5. Push and open a Pull Request

- Reference related issues (if any)
- Describe what changed and why
- Include test results or benchmarks if relevant

## 🔒 Privacy-First Contribution Rules (Critical)

All contributions must comply with Synheart’s privacy principles:

**❌ Do NOT add:**

- Content capture (text, messages, URLs)
- Keystroke logging (characters)
- Audio, microphone, or call recording
- Screen capture or screenshots
- Persistent identifiers or PII

**✅ Allowed:**

- Timing-based interaction metadata
- Aggregated counts and rates
- On-device feature extraction
- Ephemeral, in-memory processing
- Network availability status (connected / not connected), without traffic data

Any PR violating these rules will be rejected.

## 🎨 Code Style Guidelines

**Python**
- PEP 8 compliance
- Type hints required
- Max line length: 100
- Use ``black`` and ``isort``

**Dart / Flutter**
- Follow Effective Dart
- Use ``dart format``
- Respect Flutter lifecycle constraints

**Kotlin**
- Kotlin Coding Conventions
- Use ``ktlint``
- Avoid background thread abuse

**Swift**
- Swift API Design Guidelines
- Use ``swiftformat``
- Avoid blocking the main thread

## 🧪 Testing & Performance

- Tests are required for new features
- Edge cases must be covered
- No UI frame drops allowed
- Event processing must remain lightweight
- Battery and memory budgets must be respected

## 📚 Documentation Expectations

If your contribution affects:

- Public APIs → update API_REFERENCE.md
- Architecture → update ARCHITECTURE.md
- Privacy or data flow → update PRIVACY.md
- User-facing behavior → update README.md

Clear documentation is part of the contribution.

## 🐛 Reporting Bugs

Please use the GitHub Issue Tracker and include:
- Description of the issue
- Reproduction steps
- Expected vs actual behavior
- Platform, OS version, SDK version

Logs or stack traces (if applicable)

## 💡 Feature Requests

We welcome well-scoped feature requests. Please include:
- Clear use case
- Why existing signals are insufficient
- Privacy implications
- Expected outputs (features, not inferences)

## 📄 License

By contributing, you agree that your work will be licensed under the Apache 2.0 License.

## 🙏 Thank You

Thank you for helping build Synheart Behavior — a privacy-respecting foundation for understanding digital interaction patterns responsibly.

**Author**: Israel Goytom
**Organization**: Synheart Research & Engineering
