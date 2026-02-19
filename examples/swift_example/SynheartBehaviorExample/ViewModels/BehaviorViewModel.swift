import Foundation
import SynheartBehavior

@MainActor
class BehaviorViewModel: ObservableObject {

    // MARK: - Published State

    @Published var sdkInitialized = false
    @Published var sessionActive = false
    @Published var currentSessionId: String?

    // Stats
    @Published var scrollVelocity: String = "N/A"
    @Published var appSwitchesPerMin: String = "0"
    @Published var stabilityIndex: String = "N/A"
    @Published var fragmentationIndex: String = "N/A"

    // Events
    @Published var recentEvents: [EventItem] = []

    // Session results
    @Published var lastSessionSummary: BehaviorSessionSummary?
    @Published var sessionEvents: [EventItem] = []

    @Published var statusMessage = ""

    // MARK: - Private

    private let sdk: SynheartBehavior
    private var collectedEvents: [EventItem] = []

    // MARK: - Init

    init() {
        let config = BehaviorConfig(
            enableInputSignals: true,
            enableAttentionSignals: true,
            enableMotionLite: false
        )
        self.sdk = SynheartBehavior(config: config)

        do {
            try sdk.initialize()
            sdkInitialized = true
            statusMessage = "SDK initialized"
        } catch {
            statusMessage = "Init failed: \(error.localizedDescription)"
        }

        sdk.setEventHandler { [weak self] event in
            Task { @MainActor in
                guard let self else { return }
                let item = EventItem(event: event)
                self.collectedEvents.append(item)
                self.recentEvents = ([item] + self.recentEvents).prefix(20).map { $0 }
            }
        }
    }

    deinit {
        sdk.dispose()
    }

    // MARK: - Session Management

    func startSession() {
        do {
            collectedEvents.removeAll()
            let sessionId = try sdk.startSession()
            sessionActive = true
            currentSessionId = sessionId
            lastSessionSummary = nil
            sessionEvents = []
            statusMessage = "Session started: \(sessionId)"
        } catch {
            statusMessage = "Start session failed: \(error.localizedDescription)"
        }
    }

    func endSession() {
        guard let sessionId = currentSessionId else { return }
        do {
            let summary = try sdk.endSession(sessionId: sessionId)
            sessionActive = false
            currentSessionId = nil
            lastSessionSummary = summary
            sessionEvents = collectedEvents
            statusMessage = "Session ended — \(summary.eventCount) events"
        } catch {
            statusMessage = "End session failed: \(error.localizedDescription)"
        }
    }

    func refreshStats() {
        do {
            let stats = try sdk.getCurrentStats()
            scrollVelocity = stats.scrollVelocity.map { String(format: "%.1f", $0) } ?? "N/A"
            appSwitchesPerMin = "\(stats.appSwitchesPerMinute)"
            stabilityIndex = stats.stabilityIndex.map { String(format: "%.2f", $0) } ?? "N/A"
            fragmentationIndex = stats.fragmentationIndex.map { String(format: "%.2f", $0) } ?? "N/A"
            statusMessage = "Stats refreshed"
        } catch {
            statusMessage = "Refresh stats failed: \(error.localizedDescription)"
        }
    }
}

// MARK: - Event Item

struct EventItem: Identifiable {
    let id = UUID()
    let typeName: String
    let timestamp: String
    let metricsSummary: String

    init(event: BehaviorEvent) {
        self.typeName = "\(event.type)"
        self.timestamp = event.timestamp
        let metrics = event.payload.prefix(2).map { (key, value) in
            let display: String
            if let d = value as? Double {
                display = String(format: "%.1f", d)
            } else {
                display = "\(value)"
            }
            return "\(key): \(display)"
        }
        self.metricsSummary = metrics.joined(separator: " | ")
    }
}
