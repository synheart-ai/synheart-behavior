import SwiftUI

struct DashboardView: View {
    @EnvironmentObject var viewModel: BehaviorViewModel

    private let synheartRed = Color(red: 0.898, green: 0.224, blue: 0.208)
    private let synheartGreen = Color(red: 0.263, green: 0.627, blue: 0.278)
    private let synheartOrange = Color(red: 0.984, green: 0.549, blue: 0.0)
    private let synheartIndigo = Color(red: 0.224, green: 0.286, blue: 0.671)
    private let synheartBlue = Color(red: 0.118, green: 0.533, blue: 0.898)
    private let synheartGrey = Color(red: 0.459, green: 0.459, blue: 0.459)

    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    // Status section
                    Text("Status")
                        .font(.title2)
                        .fontWeight(.semibold)

                    LazyVGrid(columns: [
                        GridItem(.flexible(), spacing: 12),
                        GridItem(.flexible(), spacing: 12),
                    ], spacing: 12) {
                        StatusCardView(
                            title: "SDK Status",
                            value: viewModel.sdkInitialized ? "Ready" : "Not Init",
                            icon: "checkmark.circle.fill",
                            color: viewModel.sdkInitialized ? synheartGreen : synheartOrange
                        )
                        StatusCardView(
                            title: "Session",
                            value: viewModel.sessionActive ? "Active" : "Idle",
                            icon: "antenna.radiowaves.left.and.right",
                            color: viewModel.sessionActive ? synheartGreen : synheartGrey
                        )
                    }

                    // Current Stats
                    Text("Current Stats")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .padding(.top, 8)

                    VStack(spacing: 8) {
                        HStack {
                            statItem("Scroll Velocity", viewModel.scrollVelocity)
                            Spacer()
                            statItem("App Switches/min", viewModel.appSwitchesPerMin)
                        }
                        HStack {
                            statItem("Stability Index", viewModel.stabilityIndex)
                            Spacer()
                            statItem("Fragmentation", viewModel.fragmentationIndex)
                        }
                    }
                    .padding(16)
                    .background(synheartIndigo.opacity(0.05))
                    .cornerRadius(12)

                    // Quick Actions section
                    Text("Actions")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .padding(.top, 8)

                    FlowLayout(spacing: 8) {
                        actionButton("Start Session", icon: "play.fill", color: synheartGreen) {
                            viewModel.startSession()
                        }
                        actionButton("End Session", icon: "stop.fill", color: synheartRed) {
                            viewModel.endSession()
                        }
                        actionButton("Refresh Stats", icon: "arrow.clockwise", color: synheartBlue) {
                            viewModel.refreshStats()
                        }
                    }

                    if !viewModel.statusMessage.isEmpty {
                        Text(viewModel.statusMessage)
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                            .padding(.top, 4)
                    }
                }
                .padding(16)
            }
            .navigationTitle("Synheart Behavior")
        }
    }

    @ViewBuilder
    private func statItem(_ label: String, _ value: String) -> some View {
        VStack(alignment: .leading, spacing: 2) {
            Text(label)
                .font(.caption)
                .foregroundColor(.secondary)
            Text(value)
                .font(.headline)
                .foregroundColor(synheartIndigo)
        }
    }

    @ViewBuilder
    private func actionButton(_ label: String, icon: String, color: Color, action: @escaping () -> Void) -> some View {
        Button(action: action) {
            HStack(spacing: 6) {
                Image(systemName: icon)
                    .font(.caption)
                    .foregroundColor(color)
                Text(label)
                    .font(.subheadline)
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 8)
            .background(color.opacity(0.12))
            .cornerRadius(8)
        }
        .buttonStyle(.plain)
    }
}

// Simple flow layout for wrapping buttons
struct FlowLayout: Layout {
    var spacing: CGFloat = 8

    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        let result = layout(proposal: proposal, subviews: subviews)
        return result.size
    }

    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        let result = layout(proposal: proposal, subviews: subviews)
        for (index, position) in result.positions.enumerated() {
            subviews[index].place(
                at: CGPoint(x: bounds.minX + position.x, y: bounds.minY + position.y),
                proposal: .unspecified
            )
        }
    }

    private func layout(proposal: ProposedViewSize, subviews: Subviews) -> (size: CGSize, positions: [CGPoint]) {
        let maxWidth = proposal.width ?? .infinity
        var positions: [CGPoint] = []
        var x: CGFloat = 0
        var y: CGFloat = 0
        var rowHeight: CGFloat = 0
        var maxX: CGFloat = 0

        for subview in subviews {
            let size = subview.sizeThatFits(.unspecified)
            if x + size.width > maxWidth, x > 0 {
                x = 0
                y += rowHeight + spacing
                rowHeight = 0
            }
            positions.append(CGPoint(x: x, y: y))
            rowHeight = max(rowHeight, size.height)
            x += size.width + spacing
            maxX = max(maxX, x)
        }

        return (CGSize(width: maxX, height: y + rowHeight), positions)
    }
}
