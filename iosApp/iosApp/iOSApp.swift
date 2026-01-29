import SwiftUI
import ComposeApp
import ComposeApp

@main
struct iOSApp: App {
    init() {
        IosDiKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
