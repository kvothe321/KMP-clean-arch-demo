import SwiftUI
import ComposeApp

struct ContentView: View {
    private let viewModel: MyViewModel
    @State private var uiState: ScreenUiState = ScreenUiState(myValue: "")

    init() {
        let dependencies = IosDependencies()
        self.viewModel = dependencies.viewModel
    }

    var body: some View {
        VStack {
            Button("Click me!") {
                viewModel.doWhatever()
            }

            if !uiState.myValue.isEmpty {
                VStack(spacing: 16) {
                    Image(systemName: "swift")
                        .font(.system(size: 200))
                        .foregroundColor(.accentColor)
                    Text("ViewModel says: \(uiState.myValue)")
                }
                .transition(.move(edge: .top).combined(with: .opacity))
            } else {
                Text("Loading...")
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .onAppear {
            observeUiState()
        }
    }

    private func observeUiState() {
        Task {
            for await state in viewModel.uiState {
                uiState = state
            }
        }
    }
}
