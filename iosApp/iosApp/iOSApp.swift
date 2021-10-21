import SwiftUI
import core

@main
struct iOSApp: App {
    let presenter = DIInjector.init().KlinesListPresenter()
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(presenter: presenter))
        }
    }
}
