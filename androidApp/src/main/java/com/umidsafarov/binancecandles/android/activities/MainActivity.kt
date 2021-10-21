package com.umidsafarov.binancecandles.android.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.umidsafarov.binancecandles.android.R
import com.umidsafarov.binancecandles.android.customviews.CandlesChartView
import com.umidsafarov.binancecandles.domain.di.DIInjector
import com.umidsafarov.binancecandles.domain.entity.Kline
import com.umidsafarov.binancecandles.presentation.presenter.KlinesListNavigator
import com.umidsafarov.binancecandles.presentation.presenter.KlinesListPresenter
import com.umidsafarov.binancecandles.presentation.presenter.KlinesListView
import org.kodein.di.*

class MainActivity : AppCompatActivity(), KlinesListView, KlinesListNavigator {
    private val presenter by DIInjector.di.instance<KlinesListPresenter>()

    private lateinit var candlesChartViewView: CandlesChartView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        candlesChartViewView = findViewById(R.id.candlesChart)
        progressBarView = findViewById(R.id.progressBar)
        swipeRefreshLayout = findViewById(R.id.swipeContainer)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.getKlines(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemAccount) {
            presenter.authorize()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        presenter.attachNavigator(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
        presenter.detachNavigator()
    }

    override fun showLoading() {
        progressBarView.isVisible = true
    }

    override fun showKlines() {
        progressBarView.isVisible = false
    }

    override fun showFetchingError(message: String) {
        progressBarView.isVisible = false
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun setTitle(title: String) {
        this.title = getString(R.string.main_title, title)
    }

    override fun setKlines(klines: List<Kline>) {
        candlesChartViewView.setData(klines)
    }

    override fun openAuthorization() {
        startActivity(Intent(this, AccountSettingsActivity::class.java))
    }
}
