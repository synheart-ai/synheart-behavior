# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).



## [0.2.0] - 2026-02-06

### Added

- Correction rate and clipboard activity rate specification for typing session summaries
- Per-typing-session counts: `number_of_backspace`, `number_of_copy`, `number_of_paste`, `number_of_cut`, `number_of_delete`
- Flux-only rate computation requirement (correction_rate, clipboard_activity_rate come exclusively from Flux)

### Changed

- Behavioral and typing metric calculations now exclusively use synheart-flux
- Removed native calculation fallback from specification — Flux is required for all metric computation

### Breaking Changes

- Flux is now required; SDKs must not fall back to native metric calculations.
  - **Migration**: Remove any native `computeBehavioralMetrics()` / `computeTypingSessionSummary()` fallback paths. Ensure synheart-flux is linked and available at runtime. Use `behavioralMetrics` and `typingSessionSummary` fields directly (they now contain Flux data); remove any `behavioralMetricsFlux` / `typingSessionSummaryFlux` comparison fields.

### Platform Releases

| Platform | Version | Changelog |
|---|---|---|
| Dart | 0.2.1 | [CHANGELOG](https://github.com/synheart-ai/synheart-behavior-dart/blob/main/CHANGELOG.md) |
| Kotlin | 0.4.1 | [CHANGELOG](https://github.com/synheart-ai/synheart-behavior-kotlin/blob/main/CHANGELOG.md) |
| Swift | 0.3.0 | [CHANGELOG](https://github.com/synheart-ai/synheart-behavior-swift/blob/main/CHANGELOG.md) |
| Chrome | 0.0.1 | [CHANGELOG](https://github.com/synheart-ai/synheart-behavior-chrome/blob/main/CHANGELOG.md) |

## [0.1.0] - 2025-12-29

### Added

- Motion state inference specification (LAYING, MOVING, SITTING, STANDING) via on-device ML
- Typing session tracking and comprehensive typing metrics specification
- Deep focus block detection algorithm
- Enhanced behavioral metrics: focus hint, distraction score
- ONNX model support for motion state prediction

## [0.0.1] - 2025-12-26

### Added

- Initial specification release for Synheart Behavioral SDK
- Core SDK specification: `SynheartBehavior`, `BehaviorConfig`, `BehaviorEvent`, `BehaviorSession`, `BehaviorStats`
- Streaming API specification for real-time behavioral events
- Session tracking with summaries
- Core behavioral metrics: interaction_intensity, task_switch_rate, idle_ratio, fragmented_idle_ratio, burstiness, notification_load, scroll_jitter_rate, behavioral_distraction_score, behavioral_focus_hint, deep_focus_blocks
- Privacy-first design principles (no text, content, or PII)
- Cross-platform examples (Flutter, Kotlin, Swift)
- CONTRIBUTING.md, CODE_OF_CONDUCT.md, SECURITY.md
- Platform support: iOS 12.0+, Android API 21+, Flutter 3.10.0+
