# Synheart Behavior

**Digital behavioral signal capture — privacy-first interaction pattern analysis**

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Platform Support](https://img.shields.io/badge/platforms-Python%20%7C%20Dart%20%7C%20Kotlin%20%7C%20Swift-blue.svg)](#-sdks)

The Synheart Behavior SDK captures digital behavioral signals from the user's device to help Synheart compute behavioral patterns, focus likelihood, digital distraction, emotional correlates, cognitive workload, and behavioral fatigue.

**Privacy-First Design**: The Behavior SDK does not collect content, text, or personal user data. It collects only derived interaction metadata such as tap frequency, typing cadence variance, scroll velocity, app switching, idle duration, notification load, and micro-session fragmentation.

## 🚀 Features

- **🔒 Privacy-First**: No content extraction, no keylogging, metadata only
- **📊 Behavioral Metrics**: Tap rate, typing cadence, scroll velocity, app switching
- **⚡ On-Device Processing**: All feature computation happens locally
- **🎯 HSI Integration**: Seamless integration with HSI Runtime
- **📱 Multi-Platform**: Python, Flutter/Dart, Android/Kotlin, iOS/Swift
- **🔐 Consent-Gated**: Respects user privacy preferences
- **⚙️ Lightweight**: ≤ 1% CPU, ≤ 10MB RAM, < 0.3%/hr battery impact

## 📦 SDKs

All SDKs provide **identical functionality** with platform-idiomatic APIs. Each SDK is maintained in its own repository:

### Python SDK
```bash
pip install synheart-behavior
```
📖 **Repository**: [synheart-behavior-python](https://github.com/synheart-ai/synheart-behavior-python)

### Flutter/Dart SDK
```yaml
dependencies:
  synheart_behavior: ^0.1.0
```
📖 **Repository**: [synheart-behavior-dart](https://github.com/synheart-ai/synheart-behavior-dart)

### Android SDK (Kotlin)
```kotlin
dependencies {
    implementation("ai.synheart:behavior:0.1.0")
}
```
📖 **Repository**: [synheart-behavior-kotlin](https://github.com/synheart-ai/synheart-behavior-kotlin)

### iOS SDK (Swift)
**Swift Package Manager:**
```swift
dependencies: [
    .package(url: "https://github.com/synheart-ai/synheart-behavior-swift.git", from: "0.1.0")
]
```
📖 **Repository**: [synheart-behavior-swift](https://github.com/synheart-ai/synheart-behavior-swift)

## 📂 Repository Structure

This repository serves as the **source of truth** for shared resources across all SDK implementations:

```
synheart-behavior/
├── docs/                          # Technical documentation
│   ├── ARCHITECTURE.md            # System architecture
│   ├── API_REFERENCE.md           # API documentation
│   └── PRIVACY.md                 # Privacy guarantees
│
├── examples/                      # Cross-platform example applications
├── scripts/                       # Build and deployment scripts
└── CONTRIBUTING.md                # Contribution guidelines for all SDKs
```

**Platform-specific SDK repositories** (maintained separately):
- [synheart-behavior-python](https://github.com/synheart-ai/synheart-behavior-python) - Python SDK
- [synheart-behavior-dart](https://github.com/synheart-ai/synheart-behavior-dart) - Flutter/Dart SDK
- [synheart-behavior-kotlin](https://github.com/synheart-ai/synheart-behavior-kotlin) - Android/Kotlin SDK
- [synheart-behavior-swift](https://github.com/synheart-ai/synheart-behavior-swift) - iOS/Swift SDK

## 🎯 Quick Start

### Python

```python
from synheart_behavior import BehaviorCollector, BehaviorConfig

# Initialize collector
config = BehaviorConfig()
collector = BehaviorCollector.initialize(config)

# Start collecting events
collector.start()

# Collect interaction events
collector.collect_tap(x=100, y=200, timestamp=1234567890)
collector.collect_scroll(delta=50, velocity=2.5)
collector.collect_keystroke_timing(duration_ms=120)

# Get behavior window
window = collector.get_window()  # 30s or 5m window
print(f"Tap Rate: {window.features.tap_rate_norm}")
print(f"Distraction Score: {window.features.distraction_score}")
```

### Flutter/Dart

```dart
import 'package:synheart_behavior/synheart_behavior.dart';

// Initialize
final collector = BehaviorCollector.initialize(
  config: BehaviorConfig(),
);

// Start collecting
collector.start();

// Collect events
collector.collectTap(x: 100, y: 200);
collector.collectScroll(delta: 50, velocity: 2.5);

// Get behavior window
final window = collector.getWindow();
print('Tap Rate: ${window.features.tapRateNorm}');
```

## 🏗️ Architecture

### Behavior Signal Types

The SDK captures five categories of behavioral signals:

1. **Interaction Signals**: Taps, touch gestures, keystroke timings (NOT content), scroll events, drag gestures
2. **Activity/Inactivity Signals**: Idle periods, breaks in input, micro-session detection
3. **Environmental Digital Context**: Screen on/off, foreground app changes (app names optionally hashed), OS-level notifications (metadata only)
4. **Multitasking Signals**: Task switching frequency, interruptions (notifications triggered → user opened)
5. **Behavioral Stability Signals**: Typing cadence stability, scroll cadence stability, burstiness of interactions, session fragmentation

### Processing Pipeline

```
Raw Device Events
│
▼
Event Normalizer
│
▼
Behavior Feature Extractor
│
▼
HSI_RawBehaviorVector (x_behavior)
│
▼
HSI Runtime (fusion)
│
▼
Behavioral Heads (MLP)
│
▼
HSI State Object (behavior.{...})
```

Everything runs on-device:
- No raw events leave the device
- Only derived features → HSI → Cloud Connector → Ingest

### Behavior Features

**Interaction Metrics**:
- `tap_rate_norm`: Normalized tap frequency
- `keystroke_rate_norm`: Normalized keystroke frequency
- `scroll_velocity_norm`: Normalized scroll velocity
- `interaction_intensity`: Overall interaction intensity

**Stability Metrics**:
- `typing_cadence_stability`: Consistency of typing rhythm
- `scroll_cadence_stability`: Consistency of scrolling rhythm
- `burstiness_norm`: Variability in interaction bursts

**Digital Context Metrics**:
- `switch_rate_norm`: App/task switching frequency
- `notification_score`: Notification load and impact
- `idle_ratio`: Proportion of idle time

**Composite Scores**:
- `behavioral_distraction_score`: Overall distraction level
- `behavioral_focus_hint`: Focus likelihood indicator

## 🔒 Privacy & Security

### Hard Restrictions

- ❌ **No keylogging**: Never captures typed characters or content
- ❌ **No content extraction**: No text, URLs, messages, or screen content
- ❌ **No microphone/camera**: No audio or video capture
- ❌ **No clipboard**: No clipboard access
- ❌ **No raw notification text**: Only metadata (count, timing)

### What We Collect

✅ **Metadata Only**:
- Tap coordinates (not content)
- Keystroke timing (not characters)
- Scroll velocity (not content)
- App switching frequency (app names optionally hashed)
- Notification metadata (count, timing, not content)

### Consent Model

The Behavior SDK only activates if:
- User grants behavioral consent
- The app provides valid tenant → project → user identity
- Consent Connector SDK validates and caches policy

If user disables behavioral consent:
- Stops recording events
- Zeros out behavior features
- Marks them as `behavior_consent=false`
- HSI Runtime masks behavior features

## 📊 Performance Requirements

- **CPU**: ≤ 1% average
- **Memory**: ≤ 10MB RAM at peak
- **Wakeups**: No more than 2 wakeups/min
- **Processing**: ≤ 500µs per event
- **Frame Drops**: Never allowed in interactive UI
- **Battery**: < 0.3%/hr impact

## 🎯 Use Cases

### Powers Focus Model
- Provides behavioral inputs for focus inference
- Task switching, interaction patterns, idle detection

### Powers Emotion Model
- Behavioral correlates of emotional states
- Interaction patterns related to stress/calm

### Powers HSI Runtime
- Contributes `x_behavior` vector to HSI fusion
- Enables multimodal state inference

### Powers SWIP
- Digital behavior analytics
- App impact on focus and emotion

## 📚 Documentation

- [Architecture Guide](docs/ARCHITECTURE.md) - Detailed system architecture
- [API Reference](docs/API_REFERENCE.md) - Complete API documentation
- [Privacy Policy](docs/PRIVACY.md) - Privacy guarantees and data handling

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📄 License

Apache 2.0 License - see [LICENSE](LICENSE) for details.

## 🔗 Related Projects

- [Synheart Focus](https://github.com/synheart-ai/synheart-focus) - Cognitive concentration inference
- [Synheart Emotion](https://github.com/synheart-ai/synheart-emotion) - Physiological emotion inference
- [Synheart Core SDK](https://github.com/synheart-ai/synheart-core-sdk) - Unified SDK for all Synheart features

---

**Author**: Israel Goytom  
**Organization**: Synheart Research & Engineering

