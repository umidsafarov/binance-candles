# Binance Candles

Kotlin multiplatform applications for Android and iOS  
Simply downloads candles from Binance and draws them to Canvas

### Architecture:

CLEAN architecture and MVP.

### Usage:

Coin Pair and Candles Interval can be set in DIInjector as constant (**Symbol** and **Interval**)  
You can set Binance Key and Secret in Account Settings. But they are not required yet  
Refresh candles manually by swiping down candles chart  

### Libraries used:

- Ktor for network requests
- kotlinx.serialization for serialization
- kotlinx.coroutines for multithread operations
- Kodein for Dependency Injection
- SQLDelight to store local data

### Read plan:

- Make candles chart interactive
- Show candles chart for different coin pairs
- Refresh candles automatically
- Figure out how to change singleton values from iOS to remove companion objects from ApplicationSettings (different thread mutability issue)
- Use Jetpack Compose and MVVM architecture
- Complete iOS application
