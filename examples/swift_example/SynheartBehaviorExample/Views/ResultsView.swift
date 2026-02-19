import SwiftUI

struct ResultsView: View {
    @EnvironmentObject var viewModel: BehaviorViewModel

    private let synheartIndigo = Color(red: 0.224, green: 0.286, blue: 0.671)

    var body: some View {
        NavigationStack {
            if let summary = viewModel.lastSessionSummary {
                List {
                    // Session Info
                    Section("Session Info") {
                        metricRow("Session ID", summary.sessionId)
                        metricRow("Duration", "\(summary.duration)ms")
                        metricRow("Event Count", "\(summary.eventCount)")
                    }

                    // Behavior Metrics
                    Section("Behavior Metrics") {
                        if let sv = summary.averageScrollVelocity {
                            metricRow("Avg Scroll Velocity", String(format: "%.1f", sv))
                        }
                        if let tc = summary.averageTypingCadence {
                            metricRow("Avg Typing Cadence", String(format: "%.1f", tc))
                        }
                        metricRow("App Switch Count", "\(summary.appSwitchCount)")
                        if let si = summary.stabilityIndex {
                            metricRow("Stability Index", String(format: "%.3f", si))
                        }
                        if let fi = summary.fragmentationIndex {
                            metricRow("Fragmentation Index", String(format: "%.3f", fi))
                        }
                    }

                    // Events Timeline
                    if !viewModel.sessionEvents.isEmpty {
                        Section("Events Timeline (\(viewModel.sessionEvents.count))") {
                            ForEach(viewModel.sessionEvents) { event in
                                EventCardView(event: event)
                            }
                        }
                    }
                }
                .navigationTitle("Session Results")
            } else {
                VStack {
                    Spacer()
                    Text("End a session to see results")
                        .font(.title3)
                        .foregroundColor(.secondary)
                    Spacer()
                }
                .navigationTitle("Session Results")
            }
        }
    }

    @ViewBuilder
    private func metricRow(_ label: String, _ value: String) -> some View {
        HStack {
            Text(label)
                .font(.subheadline)
                .foregroundColor(.secondary)
            Spacer()
            Text(value)
                .font(.subheadline)
                .fontWeight(.medium)
        }
    }
}
