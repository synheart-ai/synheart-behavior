import SwiftUI

struct EventCardView: View {
    let event: EventItem

    private let typeColors: [String: Color] = [
        "scroll": Color(red: 0.118, green: 0.533, blue: 0.898),
        "tap": Color(red: 0.263, green: 0.627, blue: 0.278),
        "swipe": Color(red: 0.0, green: 0.537, blue: 0.482),
        "typing": Color(red: 0.482, green: 0.122, blue: 0.635),
        "notification": Color(red: 0.984, green: 0.549, blue: 0.0),
        "call": Color(red: 0.898, green: 0.224, blue: 0.208),
        "appSwitch": Color(red: 0.224, green: 0.286, blue: 0.671),
        "clipboard": Color(red: 0.459, green: 0.459, blue: 0.459),
    ]

    var body: some View {
        HStack(spacing: 8) {
            Text(event.typeName)
                .font(.caption2)
                .fontWeight(.medium)
                .foregroundColor(.white)
                .padding(.horizontal, 6)
                .padding(.vertical, 2)
                .background(typeColors[event.typeName] ?? Color.gray)
                .cornerRadius(4)

            VStack(alignment: .leading, spacing: 2) {
                Text("\(event.timestamp)")
                    .font(.caption2)
                    .foregroundColor(.secondary)
                if !event.metricsSummary.isEmpty {
                    Text(event.metricsSummary)
                        .font(.system(.caption, design: .monospaced))
                        .lineLimit(1)
                }
            }

            Spacer()
        }
    }
}
