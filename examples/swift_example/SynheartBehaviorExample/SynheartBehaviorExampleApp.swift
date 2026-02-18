import SwiftUI

@main
struct SynheartBehaviorExampleApp: App {
    @StateObject private var viewModel = BehaviorViewModel()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(viewModel)
        }
    }
}
