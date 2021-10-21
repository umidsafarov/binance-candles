package com.umidsafarov.binancecandles.android.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.umidsafarov.binancecandles.android.R
import com.umidsafarov.binancecandles.domain.di.DIInjector
import com.umidsafarov.binancecandles.presentation.presenter.AuthorizationNavigator
import com.umidsafarov.binancecandles.presentation.presenter.AuthorizationPresenter
import com.umidsafarov.binancecandles.presentation.presenter.AuthorizationView
import org.kodein.di.*

class AccountSettingsActivity : AppCompatActivity(), AuthorizationView, AuthorizationNavigator {
    private val presenter by DIInjector.di.instance<AuthorizationPresenter>()

    private lateinit var userKeyText: TextInputEditText
    private lateinit var userSecretText: TextInputEditText
    private lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_account)
        title = getString(R.string.authorization_title)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userKeyText = findViewById(R.id.userKeyText)
        userSecretText = findViewById(R.id.userSecretText)
        saveBtn = findViewById(R.id.saveBtn)

        saveBtn.setOnClickListener { save() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.back()
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

    override fun setCurrentUser(key: String, secret: String) {
        userKeyText.setText(key)
        userSecretText.setText(secret)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun save() {
        presenter.setUser(userKeyText.text.toString(), userSecretText.text.toString())
    }

    override fun back() {
        finish()
    }
}
