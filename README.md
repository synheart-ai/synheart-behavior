# Synheart Behavior

**Privacy-first digital behavior modeling from interaction patterns**

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Platform Support](https://img.shields.io/badge/platforms-Dart%20%7C%20Kotlin%20%7C%20Swift%20%7C%20Chrome-blue.svg)](#-sdks)


## Overview

Synheart Behavior is a cross-platform SDK for capturing and transforming digital interaction patterns into numerical behavioral signals.

The SDK models how users interact with digital systems timing, rhythm, switching, and fragmentation without ever accessing content, text, or personal data.

These behavioral signals power downstream systems such as:
- Focus and distraction inference
- Digital wellness analytics
- Cognitive load and fatigue estimation
- Multimodal human state modeling (HSI)

Important:
Synheart Behavior analyzes interaction dynamics, not what users type, read, or see.


## Core Principles

- Privacy-by-design
- Metadata only (never content)
- Permission-scoped tracking
- On-device aggregation first
- Numerical, interpretable representations


## 🚀 Key Features

🔒 **Privacy-First**: No content, no text, no audio, no screen capture

📊 **Behavioral Metrics**: Interaction intensity, task switching, idle fragmentation, burstiness, distraction and focus proxies

⚡ **On-Device Processing**: Feature extraction and aggregation run locally

🎯 **HSI-Ready**: Produces structured behavioral vectors for Synheart HSI fusion

📱 **Multi-Platform SDKs**: Flutter/Dart, Android (Kotlin), iOS (Swift), Chrome Extension

🔐 **Consent-Gated**: Fully controlled by user permission and policy enforcement

🪶 **Lightweight**: Designed for continuous background operation with minimal CPU and battery impact


## 📦 SDKs

All SDKs provide **identical functionality** with platform-idiomatic APIs. Each SDK is maintained in its own repository:

### Flutter/Dart SDK
```yaml
dependencies:
  synheart_behavior: ^0.2.1
```
📖 **Repository**: [synheart-behavior-dart](https://github.com/synheart-ai/synheart-behavior-dart)

### Android SDK (Kotlin)
```kotlin
dependencies {
    implementation("ai.synheart:behavior:0.4.1")
}
```
📖 **Repository**: [synheart-behavior-kotlin](https://github.com/synheart-ai/synheart-behavior-kotlin)

### iOS SDK (Swift)
**Swift Package Manager:**
```swift
dependencies: [
    .package(url: "https://github.com/synheart-ai/synheart-behavior-swift.git", from: "0.3.0")
]
```
📖 **Repository**: [synheart-behavior-swift](https://github.com/synheart-ai/synheart-behavior-swift)

### Chrome Extension
Load unpacked from [synheart-behavior-chrome](https://github.com/synheart-ai/synheart-behavior-chrome) or install from a release ZIP.

📖 **Repository**: [synheart-behavior-chrome](https://github.com/synheart-ai/synheart-behavior-chrome)


## 📂 Repository Structure

This repository serves as the **canonical specification hub** for shared resources across all SDK implementations:

```
project-name/
 ├─ docs/                     # Necessary documents of the repo/SDK
 ├─ models/                   # ML Models that the SDK is going to use
 ├─ examples/                 # Examples how you can use the SDK
 ├─ scripts/                  # where the SDK exists 
 ├─ .github/                  # Github workflow of CI/CD
 │   ├─ ISSUE_TEMPLATE/
 │   ├─ workflows/
 ├─ CONTRIBUTING.md           # A guideline how to contribute on this SDK 
 ├─ CODE_OF_CONDUCT.md
 ├─ SECURITY.md
 ├─ LICENSE                   # A LICENSE NOTICE                 
 └─ README.md
```

**Platform-specific SDK repositories** (maintained separately):
- [synheart-behavior-dart](https://github.com/synheart-ai/synheart-behavior-dart) - Flutter/Dart SDK
- [synheart-behavior-kotlin](https://github.com/synheart-ai/synheart-behavior-kotlin) - Android/Kotlin SDK
- [synheart-behavior-swift](https://github.com/synheart-ai/synheart-behavior-swift) - iOS/Swift SDK
- [synheart-behavior-chrome](https://github.com/synheart-ai/synheart-behavior-chrome) - Chrome Extension


## 🏗️ Architecture

### Behavioral Model

**What Is Collected**
The SDK captures event-level interaction metadata, such as:
- Tap, typing, scroll, swipe events (timing + physical properties only)
- App foreground/background transitions
- Idle gaps and interaction pauses
- Notification and call events (event only, no content)
- Motion state (sitting, standing, moving, laying)

**What Is Not Collected**

- Typed characters or text
- Notification content or sender identity
- Call audio or voice data
- Screenshots or screen recordings
- URLs, app UI data, or semantics
- Clipboard, camera, or microphone data

### Events and Sessions
**Event**
An event is a single atomic interaction: tap, typing, scroll, swipe, notification, call, idle_gap.
Each event contains:
- timestamp
- event type
- session ID
- non-semantic metrics (e.g., duration, velocity)

**Session**
A session is a continuous period of interaction with an application, bounded by: app open / close, or inactivity ≥ idle threshold (e.g., 30s)

Sessions are the primary unit for short-term behavioral aggregation.

### Processing Pipeline

```
Raw Interaction Events
│
▼
Event Normalization
│
▼
Session Aggregation
│
▼
Behavior Feature Computation
│
▼
Normalized Behavioral Vector
│
▼
HSI Runtime / Downstream Consumers
```

Everything runs on-device:
- Raw events are processed locally
- Only aggregated features are exposed
- No raw interaction logs are transmitted by default

### Core Behavioral Metrics

Session-level outputs:
- interaction_intensity
- task_switch_rate
- task_switch_cost
- idle_ratio
- fragmented_idle_ratio
- burstiness
- notification_load
- scroll_jitter_rate
- behavioral_distraction_score
- behavioral_focus_hint
- deep_focus_blocks

Daily aggregation produces higher-level behavioral summaries such as:
- fragmented time ratio
- screen time segments (morning / afternoon / evening / night)
- recovery-friendly minutes
- multitasking intensity
- behavioral stability score
- habit strength index

All metrics are bounded, normalized, and numerically stable.


## 🔒 Privacy & Compliance

**Hard Guarantees**

✅ No PII

✅ No content capture

✅ No keystroke logging

✅ No audio or visual recording

✅ Permission-scoped tracking only

✅ No tracking across unconsented apps

**Connectivity Model**
- The SDK does not require internet, Bluetooth, or external connectivity to operate.
- It may record a binary network availability state (online/offline) as contextual metadata.
- No network traffic, destinations, or payloads are inspected or captured.
- Any data transmission is explicitly consent-gated and configurable.

**Regulatory Alignment**
- GDPR / CCPA aligned
- Data minimization and purpose limitation enforced
- App Tracking Transparency (ATT) not required

## ⚙️ Performance Targets

- **CPU**: ≤ 1% average
- **Memory**: ≤ 10 MB peak
- **Battery**: < 0.3% per hour
- **Event processing**: < 500 μs
- **UI**: No UI thread blocking


## 🎯 Use Cases

- HSI Runtime — multimodal state fusion
- Focus & Distraction Modeling
- Digital Wellness (SWIP)
- Behavior-Emotion Correlation
- Longitudinal Habit Analysis


## Consent Model

The Behavior SDK only activates if:
- User grants behavioral consent
- The app provides valid tenant → project → user identity
- Consent Connector SDK validates and caches policy

If user disables behavioral consent:
- Stops recording events
- Zeros out behavior features
- Marks them as `behavior_consent=false`
- HSI Runtime masks behavior features


## 📚 Documentation

- [Contributing Guide](CONTRIBUTING.md) - How to contribute (covers all SDKs)
- [Changelog](CHANGELOG.md) - Specification version history
- [Security Policy](SECURITY.md) - Vulnerability reporting and security practices
- [Code of Conduct](CODE_OF_CONDUCT.md) - Community guidelines


## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.


## 📄 License

Apache 2.0 License - see [LICENSE](LICENSE) for details.


## 🔗 Related Projects

| Repository | Description |
|---|---|
| [synheart-behavior-dart](https://github.com/synheart-ai/synheart-behavior-dart) | Flutter/Dart SDK |
| [synheart-behavior-kotlin](https://github.com/synheart-ai/synheart-behavior-kotlin) | Android/Kotlin SDK |
| [synheart-behavior-swift](https://github.com/synheart-ai/synheart-behavior-swift) | iOS/Swift SDK |
| [synheart-behavior-chrome](https://github.com/synheart-ai/synheart-behavior-chrome) | Chrome Extension |
| [synheart-focus](https://github.com/synheart-ai/synheart-focus) | Cognitive concentration inference |
| [synheart-emotion](https://github.com/synheart-ai/synheart-emotion) | Physiological emotion inference |
| [synheart-core](https://github.com/synheart-ai/synheart-core) | Core orchestration specification |

---

**Author**: Israel Goytom  
**Organization**: Synheart Research & Engineering

