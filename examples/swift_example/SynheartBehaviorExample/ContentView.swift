import SwiftUI

struct ContentView: View {
    @EnvironmentObject var viewModel: BehaviorViewModel

    var body: some View {
        TabView {
            DashboardView()
                .tabItem {
                    Label("Dashboard", systemImage: "square.grid.2x2")
                }

            InteractionView()
                .tabItem {
                    Label("Interaction", systemImage: "hand.tap")
                }

            ResultsView()
                .tabItem {
                    Label("Results", systemImage: "list.bullet.rectangle")
                }
        }
        .accentColor(Color(red: 0.224, green: 0.286, blue: 0.671))
    }
}
