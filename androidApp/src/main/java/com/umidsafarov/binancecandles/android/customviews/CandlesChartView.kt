package com.umidsafarov.binancecandles.android.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.umidsafarov.binancecandles.android.R
import com.umidsafarov.binancecandles.domain.entity.Kline

class CandlesChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context) : this(context, null)

    //theming
    private var candlesPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics)
    private var candleColorLong = Color.GREEN
    private var candleColorShort = Color.RED


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CandlesChartView,
            0, 0
        ).apply {
            try {
                candlesPadding = getDimension(R.styleable.CandlesChartView_barsPadding, candlesPadding)
                candleColorLong = getColor(R.styleable.CandlesChartView_colorCandleLong, candleColorLong)
                candleColorShort = getColor(R.styleable.CandlesChartView_colorCandleShort, candleColorShort)
            } finally {
                recycle()
            }
        }
    }

    //view area (todo make it react to swipe, pinch, e.g.)
    private var startCandleIndex = 0
    private var candlesCount = 20
    private var minValue = 0f
    private var maxValue = 0f

    //measure
    private var viewWidth = 0
    private var viewHeight = 0
    private var candleWidth = 0f
    private var tailOffset = 0f
    private var screenValuePerCandleValue = 1f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
        measureCandles()
    }

    private fun measureCandles() {
        if (klines.isEmpty())
            return

        minValue = klines[startCandleIndex].low.toFloat()
        maxValue = klines[startCandleIndex].high.toFloat()
        lateinit var kline: Kline
        for (i in 0 until candlesCount) {
            kline = klines[startCandleIndex + i]
            minValue = minValue.coerceAtMost(kline.low.toFloat())
            maxValue = maxValue.coerceAtLeast(kline.high.toFloat())
        }

        candleWidth = viewWidth.toFloat() / candlesCount
        tailOffset = candleWidth * .4f
        screenValuePerCandleValue = viewHeight.toFloat() / (maxValue - minValue)
    }

    //draw
    private var paint = Paint()
    override fun onDraw(canvas: Canvas?) {
        if (canvas == null || klines.isEmpty())
            return

        lateinit var kline: Kline
        for (i in 0 until candlesCount) {
            kline = klines[startCandleIndex + i]

            paint.setColor(
                if (kline.open < kline.close) candleColorLong else candleColorShort
            )
            canvas.drawRect(
                i * candleWidth + tailOffset,
                viewHeight - (kline.low.toFloat() - minValue) * screenValuePerCandleValue,
                (i + 1) * candleWidth - tailOffset,
                viewHeight - (kline.high.toFloat() - minValue) * screenValuePerCandleValue,
                paint
            )
            canvas.drawRect(
                i * candleWidth + candlesPadding,
                viewHeight - (kline.open.toFloat() - minValue) * screenValuePerCandleValue,
                (i + 1) * candleWidth - candlesPadding,
                viewHeight - (kline.close.toFloat() - minValue) * screenValuePerCandleValue,
                paint
            )
        }
    }

    //data
    private val klines = mutableListOf<Kline>()

    fun setData(klines: List<Kline>) {
        this.klines.clear()
        this.klines.addAll(klines)

        candlesCount = candlesCount.coerceAtMost(klines.size)
        startCandleIndex = klines.size - candlesCount

        measureCandles()
        invalidate()
    }
}