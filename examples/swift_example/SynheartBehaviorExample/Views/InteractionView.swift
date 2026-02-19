import SwiftUI

struct InteractionView: View {
    @EnvironmentObject var viewModel: BehaviorViewModel
    @State private var typingText = ""

    private let synheartPurple = Color(red: 0.482, green: 0.122, blue: 0.635)
    private let synheartBlue = Color(red: 0.118, green: 0.533, blue: 0.898)

    var body: some View {
        NavigationStack {
            List {
                // Typing test section
                Section("Typing Test") {
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Type below to generate typing events:")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                        TextField("Start typing here...", text: $typingText, axis: .vertical)
                            .lineLimit(3...6)
                            .textFieldStyle(.roundedBorder)
                    }
                    .padding(.vertical, 4)
                }

                // Test items for scroll/tap
                Section("Test Items (Scroll & Tap)") {
                    ForEach(1...20, id: \.self) { index in
                        VStack(alignment: .leading, spacing: 4) {
                            Text("Test Item #\(index)")
                                .font(.headline)
                            Text("Tap or scroll through this list to generate behavioral events")
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        .padding(.vertical, 4)
                    }
                }

                // Live event feed
                Section("Live Event Feed") {
                    if viewModel.recentEvents.isEmpty {
                        Text("No events yet — interact with the app to generate events")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    } else {
                        ForEach(viewModel.recentEvents) { event in
                            EventCardView(event: event)
                        }
                    }
                }
            }
            .navigationTitle("Interaction Test")
        }
    }
}
