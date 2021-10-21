import SwiftUI
import core

struct ContentView: View {
  @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        NavigationView {
            listView()
                .navigationBarTitle(viewModel.title)
                .navigationBarItems(trailing: Button("Reload") {
                    self.viewModel.presenter.getKlines(forceReload: true)
                })
        }.navigationViewStyle(StackNavigationViewStyle())
    }

    private func listView() -> AnyView {
        switch viewModel.klines {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let klines):
            return AnyView(List(klines) { kline in
                KlineRow(kline: kline)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}

extension ContentView {

    enum LoadableKlines {
        case loading
        case result([Kline])
        case error(String)
    }

    class ViewModel: ObservableObject, KlinesListView {
        var presenter: KlinesListPresenter!
        var title:String = "Binance Candles"
        var klinesCache:[Kline] = [Kline]()
        @Published var klines = LoadableKlines.loading
        
        func setTitle(title: String) {
            self.title = title
        }
        
        func setKlines(klines: [Kline]) {
            self.klinesCache = klines
        }
        
        func showFetchingError(message: String) {
            self.klines = .error(message)
        }
        
        func showKlines() {
            self.klines = .result(klinesCache)
        }
        
        func showLoading() {
            self.klines = .loading
        }

        init(presenter: KlinesListPresenter) {
            self.presenter = presenter
            self.presenter.attachView(view: self)
        }
    }
}

extension Kline: Identifiable { }
