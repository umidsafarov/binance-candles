import SwiftUI
import core

struct KlineRow: View {
    var kline: Kline

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0) {
                Text("Volume: \(kline.volume)").foregroundColor(textColor)
            }
            Spacer()
        }
    }
}

extension KlineRow {
   private var textColor: Color {
       if kline.open > kline.close {
           return Color.black
       } else {
           return Color.gray
       }
   }
}
